package com.re_form_shop_2605.service.payment;

import com.re_form_shop_2605.dto.payment.PointHistoryItemDTO;
import com.re_form_shop_2605.dto.payment.PointWalletResponseDTO;
import com.re_form_shop_2605.dto.payment.WithdrawRequestDTO;
import com.re_form_shop_2605.dto.payment.WithdrawResponseDTO;
import com.re_form_shop_2605.entity.Enum.PointRequestStatus;
import com.re_form_shop_2605.entity.payment.PointHistory;
import com.re_form_shop_2605.entity.payment.PointRequest;
import com.re_form_shop_2605.entity.payment.PointWallet;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.payment.PointHistoryRepository;
import com.re_form_shop_2605.repository.payment.PointRequestRepository;
import com.re_form_shop_2605.repository.payment.PointWalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointWalletRepository pointWalletRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final PointRequestRepository pointRequestRepository;
    private final MemberRepository memberRepository;

    /* 1. 포인트 지갑 조회 */
    public PointWalletResponseDTO getPointWallet(Long memberId) {
        // 1) memberId로 포인트 지갑 조회
        PointWallet response = pointWalletRepository.findByMemberMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("getPointWallet : 포인트 지갑이 없습니다."));

        // 2) DTO 변환
        return new PointWalletResponseDTO(
                response.getBalance(),
                response.getWithdrawable(),
                response.getPending()
        );
    }

    /* 2. 포인트 이력 조회 */
    public List<PointHistoryItemDTO> getPointHistory(Long memberId) {
        // 1) memberId로 포인트 지갑 조회
        PointWallet wallet = pointWalletRepository.findByMemberMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("getPointHistory : 포인트 지갑이 없습니다."));

        // 2) walletId로 포인트 이력 조회
        List<PointHistory> historyList = pointHistoryRepository.findByPointWalletWalletIdOrderByCreatedAtDesc(wallet.getWalletId());

        // 3) DTO 변환
        List<PointHistoryItemDTO> responses = new ArrayList<>();
        for (PointHistory history : historyList) {
            PointHistoryItemDTO dto = new PointHistoryItemDTO(
                    history.getPointId(),
                    history.getType(),
                    history.getChangeAmount(),
                    history.getBalance(),
                    history.getTrade() != null ? history.getTrade().getTradeId() : null,
                    history.getCreatedAt());

            responses.add(dto);
        }
        return responses;
    }

    /* 3. 포인트 출금 요청 */
    public WithdrawResponseDTO requestWithdraw(Long memberId, WithdrawRequestDTO request) {
        // 1) memberId로 PointWallet 조회 -> 출금 가능 포인트 확인
        PointWallet pointWallet = pointWalletRepository.findByMemberMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("requestWithdraw : 포인트 지갑이 없습니다."));
        int withdrawable = pointWallet.getWithdrawable();

        // 2) 중복 요청 확인 : PENDING 상태 출금 요청 유무 확인
        if (pointRequestRepository.existsByMemberMemberIdAndStatus(memberId, PointRequestStatus.PENDING)) {
            throw new IllegalStateException("requestWithdraw : 이미 진행 중인 출금 요청 건이 있습니다.");
        }

        // 3) 출금 가능 포인트 검증
        if (request.requestAmount() > withdrawable) {
            throw new IllegalArgumentException("requestWithdraw : 출금 가능한 포인트가 부족합니다.");
        }

        // 4) PointRequest 저장
        PointRequest pointRequest = PointRequest.builder()
                .member(pointWallet.getMember())
                .requestAmount(request.requestAmount())
                .bankName(request.bankName())
                .accountNumber(request.accountNumber())
                .build();
        pointRequestRepository.save(pointRequest);

        // 5) PointWallet 업데이트 : withdrawable 차감, pending 증가
        pointWallet.withdraw(request.requestAmount());

        // 6) WithdrawResponseDTO 반환
        return new WithdrawResponseDTO(
                pointRequest.getWithdrawId(),
                request.requestAmount(),
                request.bankName(),
                request.accountNumber(),
                PointRequestStatus.PENDING,
                LocalDateTime.now()
        );
    }

    /* 4. 회원별 출금 요청 목록 조회 */
    public List<WithdrawResponseDTO> getRequestWithdrawList(Long memberId) {
        List<PointRequest> requests = pointRequestRepository.findByMemberMemberIdOrderByCreatedAtDesc(memberId);

        List<WithdrawResponseDTO> responses = new ArrayList<>();
        for (PointRequest request : requests) {
            WithdrawResponseDTO response = new WithdrawResponseDTO(
                    request.getWithdrawId(),
                    request.getRequestAmount(),
                    request.getBankName(),
                    request.getAccountNumber(),
                    request.getStatus(),
                    request.getCreatedAt()
            );
            responses.add(response);
        }

        return responses;
    }
}