package com.re_form_shop_2605.dto.payment;

import com.re_form_shop_2605.entity.Enum.PayMethod;
import com.re_form_shop_2605.entity.Enum.PaymentStatus;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-08
 * 설명: 결제 응답 DTO (백 → 프론트)
 *      - 결제 조회, 승인, 취소 공통 응답
 * ─────────────────────────────────────────────────────
 */

public record PaymentResponseDTO(
        Long paymentId, Long tradeId,
        PayMethod payMethod, int amount, PaymentStatus status,
        String approvalNo, LocalDateTime paidAt
) {
    /* 결제 응답 DTO */
}
