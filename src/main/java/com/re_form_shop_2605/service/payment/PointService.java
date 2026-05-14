package com.re_form_shop_2605.service.payment;

import com.re_form_shop_2605.dto.admin.AdminWithdrawActionRequestDTO;
import com.re_form_shop_2605.dto.admin.WithdrawAction;
import com.re_form_shop_2605.dto.payment.PointHistoryItemDTO;
import com.re_form_shop_2605.dto.payment.PointWalletResponseDTO;
import com.re_form_shop_2605.dto.payment.WithdrawRequestDTO;
import com.re_form_shop_2605.dto.payment.WithdrawResponseDTO;
import com.re_form_shop_2605.entity.Enum.PointHistoryType;
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

/**
 * 작성자: 손민정
 * 작성일: 2026-05-11
 * 설명: 포인트/출금 비즈니스 로직
 *       - 포인트 지갑 조회, 이력 조회
 *       - 출금 요청/취소/승인/반려 처리
 */

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
        pointWalletRepository.save(pointWallet);

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
    public List<WithdrawResponseDTO> getMemberRequestWithdrawList(Long memberId) {
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

    /* 5. 관리자 출금 요청 목록 조회 */
    public List<WithdrawResponseDTO> getPendingWithdrawList() {
        List<PointRequest> requestList = pointRequestRepository.findByStatusOrderByCreatedAtAsc(PointRequestStatus.PENDING);

        List<WithdrawResponseDTO> responses = new ArrayList<>();
        for (PointRequest request : requestList) {
            WithdrawResponseDTO responseDTO = new WithdrawResponseDTO(
                    request.getWithdrawId(),
                    request.getRequestAmount(),
                    request.getBankName(),
                    request.getAccountNumber(),
                    request.getStatus(),
                    request.getCreatedAt()
            );
            responses.add(responseDTO);
        }

        return responses;
    }

    /* 6. 포인트 출금 요청 승인/반려 처리 */
    public WithdrawResponseDTO withdrawalApprovalRejection(Long withdrawId, AdminWithdrawActionRequestDTO request) {
        // 1) withdrawId로 PointRequest 조회
        PointRequest pointRequest = pointRequestRepository.findById(withdrawId)
                .orElseThrow(() -> new IllegalArgumentException("withdrawalApprovalRejection : 요청 내역이 없습니다."));

        // 2) PENDING 상태인지 검증
        if (pointRequest.getStatus() != PointRequestStatus.PENDING) {
            throw new IllegalStateException("withdrawalApprovalRejection : 출금 요청 대기 상태가 아닙니다.");
        }

        // 3) action에 따라 처리
        if (request.action() == WithdrawAction.APPROVED) {
            pointRequest.approve();
            PointWallet wallet = pointWalletRepository.findByMemberMemberId(pointRequest.getMember().getMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("withdrawalApprovalRejection : 포인트 지갑이 존재하지 않습니다."));

            wallet.approvedWithdraw(pointRequest.getRequestAmount());
            pointWalletRepository.save(wallet); // wallet 업데이트
            pointHistoryRepository.save(PointHistory.builder() // point history 업데이트
                    .pointWallet(wallet)
                    .type(PointHistoryType.WITHDRAW)
                    .changeAmount(-pointRequest.getRequestAmount())
                    .balance(wallet.getBalance())
                    .build());

        } else if (request.action() == WithdrawAction.REJECTED) {
            pointRequest.reject(request.rejectReason());
            PointWallet wallet = pointWalletRepository.findByMemberMemberId(pointRequest.getMember().getMemberId())
                    .orElseThrow(() -> new IllegalArgumentException("withdrawalApprovalRejection : 포인트 지갑이 존재하지 않습니다."));
            wallet.rejectedWithdraw(pointRequest.getRequestAmount());
            pointWalletRepository.save(wallet); // wallet 업데이트
        }
        pointRequestRepository.save(pointRequest);

        // 4) WithdrawResponseDTO 반환
        return new WithdrawResponseDTO(
                pointRequest.getWithdrawId(),
                pointRequest.getRequestAmount(),
                pointRequest.getBankName(),
                pointRequest.getAccountNumber(),
                pointRequest.getStatus(),
                pointRequest.getCreatedAt()
        );
    }

    /* 7. 고객 출금 요청 취소 */
    public void cancelWithdraw(Long memberId, Long withdrawId) {
        // 1) 취소 할 출금 요청 내역 찾기
        PointRequest request = pointRequestRepository.findById(withdrawId)
                .orElseThrow(() -> new IllegalArgumentException("cancelWithdraw : 출금 요청 내역이 없습니다."));

        // 2) 본인 요청인지 검증
        if (!request.getMember().getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("cancelWithdraw : 본인의 출금 요청만 취소 가능합니다.");
        }

        // 3) PENDING 상태인지 검증
        if (request.getStatus() != PointRequestStatus.PENDING) {
            throw new IllegalStateException("cancelWithdraw : 대기 중인 출금 요청만 취소 가능합니다.");
        }

        // 4) 상태 변경
        request.cancel();
        pointRequestRepository.save(request);

        // 5) PointWallet 복구
        PointWallet pointWallet = pointWalletRepository.findByMemberMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("cancelWithdraw : 포인트 지갑을 찾을 수 없습니다."));
        pointWallet.rejectedWithdraw(request.getRequestAmount());
        pointWalletRepository.save(pointWallet);
    }
}