package com.re_form_shop_2605.service.payment;

import com.re_form_shop_2605.dto.payment.*;
import com.re_form_shop_2605.entity.Enum.PaymentStatus;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.TradeDeliveryType;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.entity.payment.Payment;
import com.re_form_shop_2605.entity.payment.PointWallet;
import com.re_form_shop_2605.entity.payment.TossLog;
import com.re_form_shop_2605.entity.trade.Trade;
import com.re_form_shop_2605.repository.payment.PaymentRepository;
import com.re_form_shop_2605.repository.payment.PointWalletRepository;
import com.re_form_shop_2605.repository.payment.TossLogRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 작성자: 손민정
 * 작성일: 2026-05-09
 * 설명: 결제 비즈니스 로직
 *      - 토스페이먼츠 API 연동
 *      - 결제 초기화/승인/취소/환불 처리
 */

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final TradeRepository tradeRepository;
    private final TossLogRepository tossLogRepository;
    private final PointWalletRepository pointWalletRepository;
    private final WebClient tossWebClient;

    // 1. 결제 요청
    public PaymentInitResponseDTO createPayment(Long buyerId, PaymentInitRequestDTO request) {
        // 1) 거래 조회 -> 거래 반환
        Trade trade = tradeRepository.findById(request.tradeId())
                .orElseThrow(() -> new IllegalArgumentException("createPayment : 존재하지 않는 거래입니다."));

        // 2) 요청자가 구매자인지 검증
        if (!trade.getBuyer().getMemberId().equals(buyerId)) {
            throw new IllegalArgumentException("createPayment : 구매자가 아닌 사용자는 결제 권한이 없습니다.");
        }

        // 3) 거래 상태 검증 (ACCEPTED가 아니면 결제 불가)
        if (trade.getStatus() != TradeStatus.ACCEPTED) {
            throw new IllegalArgumentException("createPayment : ACCEPTED 상태가 아닌 거래 건은 결제할 수 없습니다.");
        }

        // 4) 결제 가능 거래 방식 및 배송지 정보 검증
        if (trade.getDeliveryType() != TradeDeliveryType.DELIVERY) {
            throw new IllegalArgumentException("createPayment : 택배 거래만 결제를 진행할 수 있습니다.");
        }

        if (trade.getDeliveryAddress() == null || trade.getDeliveryAddress().isBlank()) {
            throw new IllegalArgumentException("createPayment : 배송 거래는 결제 전에 배송지 정보가 필요합니다.");
        }

        // 5) tossOrderId 생성 후 저장 => UUID 이용
        String tossOrderId = UUID.randomUUID().toString();

        // 6) Payment 생성 및 DB insert
        Payment payment = Payment.builder()
                .trade(trade)
                .payMethod(request.payMethod())
                .tossOrderId(tossOrderId)
                .amount(trade.getTradePrice())
                .status(PaymentStatus.READY)
                .build();
        paymentRepository.save(payment);

        // 7) PaymentInitResponseDTO 반환
        return new PaymentInitResponseDTO(
                tossOrderId,
                trade.getPost().getTitle(),
                trade.getTradePrice()
        );
    }


    // 2. toss 승인 처리 콜백
    public PaymentResponseDTO confirmPayment(PaymentConfirmRequestDTO request) {
        // 1) orderId로 payment 조회
        Payment payment = paymentRepository.findByTossOrderId(request.orderId())
                .orElseThrow(() -> new IllegalArgumentException("confirmPayment : 존재하지 않는 결제 건입니다."));

        // 2) 금액 검증 (위변조 방지)
        if (!payment.getAmount().equals(request.amount())) {
            throw new IllegalArgumentException("confirmPayment : 금액이 불일치합니다.");
        }

        // 3) 요청 Body 생성 및 토스 승인 API 호출
        Map<String, Object> tossRequest = new HashMap<>();
        tossRequest.put("paymentKey", request.paymentKey());
        tossRequest.put("orderId", request.orderId());
        tossRequest.put("amount", request.amount());

        // ** post, 응답은 Map 형식으로 받음, 동기 **
        Map response = null;
        try { // 결제 성공 시
            response = tossWebClient.post()
                    .uri("/v1/payments/confirm")
                    .bodyValue(tossRequest)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch(Exception e) { // 결제 실패 시
            payment.fail();
            throw new RuntimeException("토스 결제 승인 실패 : " + e.getMessage());
        }

        // 4) payment 상태 업데이트 (PAID)
        String tossPaymentKey = (String) response.get("paymentKey");
        Map cardInfo = (Map) response.get("card");
        String approvalNo = cardInfo != null ? (String) cardInfo.get("approveNo") : null;
        payment.paid(tossPaymentKey, approvalNo);

        // 5) trade 상태 업데이트 (PAID)
        Trade trade = payment.getTrade();
        trade.markPaid();

        // 6) 판매자 pending 업데이트
        PointWallet sellerWallet = pointWalletRepository.findByMemberMemberId(trade.getSeller().getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("confirmPayment : 판매자 포인트 지갑이 존재하지 않습니다."));
        sellerWallet.earnPoint(trade.getTradePrice());

        // 7) toss_log 저장
        tossLogRepository.save(
                TossLog.builder()
                        .payment(payment)
                        .tossPaymentKey(tossPaymentKey)
                        .rawResponse(response.toString())
                        .build()
        );

        // 7) PaymentResponseDTO 반환
        return new PaymentResponseDTO(
                payment.getPaymentId(), trade.getTradeId(),
                payment.getPayMethod(), payment.getAmount(), payment.getStatus(),
                payment.getApprovalNo(), payment.getPaidAt()
        );
    }

    // 3. 결제 취소
    // 3-1. toss 결제 취소 api 호출
    private void callTossCancelAPI(String tossPaymentKey, String cancelReason) {
        // 1) 요청 Body 생성
        Map<String, Object> tossCancelRequest = new HashMap<>();
        tossCancelRequest.put("cancelReason", cancelReason);

        // 2) 토스 취소 API 호출
        Map response = tossWebClient.post()
                .uri("/v1/payments/{paymentKey}/cancel", tossPaymentKey)
                .bodyValue(tossCancelRequest)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    // 3-2. 결제 취소 메서드
    public PaymentResponseDTO cancelPayment(String tossPaymentKey, PaymentCancelRequestDTO request) {
        // 1) tossPaymentKey로 payment 조회
        Payment payment = paymentRepository.findByTossPaymentKey(tossPaymentKey)
                .orElseThrow(() -> new IllegalArgumentException("cancelPayment : 존재하지 않는 tossPaymentKey 입니다."));

        // 2) payment 상태 검증 (PAID 상태만 취소 가능)
        if (payment.getStatus() != PaymentStatus.PAID) {
            throw new IllegalArgumentException("cancelPayment : PAID 상태가 아닌 거래 건의 결제를 취소할 수 없습니다.");
        }

        // 3) cancelType에 따라 payment 상태 변경 : CANCEL  → payment.cancel() / REFUND  → payment.refund()
        if (request.cancelType().equals("CANCEL")) {
            payment.cancel();
        } else if (request.cancelType().equals("REFUND")) {
            payment.refund();
        }

        // 4) callTossCancelApi() 호출
        callTossCancelAPI(tossPaymentKey, request.cancelReason());

        // 5) trade 상태 변경 → CANCELED
        Trade trade = payment.getTrade();
        trade.cancel();
        trade.getPost().changeStatus(PostStatus.ON_SALE);

        // 6) PaymentResponseDTO 반환
        return new PaymentResponseDTO(
                payment.getPaymentId(), trade.getTradeId(),
                payment.getPayMethod(), payment.getAmount(), payment.getStatus(),
                payment.getApprovalNo(), payment.getPaidAt()
        );
    }

    // 4. 결제 정보 조회
    public PaymentResponseDTO getPayment(Long tradeId) {
        // 1) 해당 거래의 결제 내역이 존재 여부 확인
        Payment payment = paymentRepository.findByTradeTradeId(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("getPayment : 해당 거래의 결제 내역이 존재하지 않습니다."));

        // 2) PaymentResponseDTO 반환
        return new PaymentResponseDTO(payment.getPaymentId(), tradeId,
                payment.getPayMethod(), payment.getAmount(), payment.getStatus(),
                payment.getApprovalNo(), payment.getPaidAt());
    }
}
