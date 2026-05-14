package com.re_form_shop_2605.dto.payment;

import com.re_form_shop_2605.entity.Enum.PointRequestStatus;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-11
 * 설명: 포인트 출금 응답 DTO (백 → 프론트)
 *       - 출금 신청, 목록 조회 공통 응답
 * ─────────────────────────────────────────────────────
 */

public record WithdrawResponseDTO(
        Long withdrawId,
        int requestAmount,
        String bankName,
        String accountNumber,
        PointRequestStatus status, // PENDING(대기) | APPROVED(승인) | REJECTED(반려) | CANCELED(취소)
        LocalDateTime createdAt
) {
}
