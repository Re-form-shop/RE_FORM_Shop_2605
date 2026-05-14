package com.re_form_shop_2605.dto.payment;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-08
 * 설명: 결제 초기화 응답 DTO (백 → 프론트)
 * ─────────────────────────────────────────────────────
 */

public record PaymentInitResponseDTO(
        String tossOrderId, // 프론트에서 토스 결제창에 전달
        String orderName,   // 상품명 (토스 결제창 표시용)
        int amount          // 결제 금액
) {
    /* 결제 초기화 응답 DTO (백 -> 프론트) */
}