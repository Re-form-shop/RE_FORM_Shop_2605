package com.re_form_shop_2605.dto.payment;

import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-08
 * 설명: 토스 결제 승인 요청 DTO (프론트 → 백)
 *      - 프론트가 토스로부터 받은 콜백 값을 백엔드에 전달
 * ─────────────────────────────────────────────────────
 */

public record PaymentConfirmRequestDTO(
        @NotBlank String paymentKey, // 토스 결제 키
        @NotBlank String orderId,    // 주문 ID
        @NotNull Integer amount      // 결제 금액
) {
    /* 결제 승인 요청 DTO (프론트 -> 백) */
}
