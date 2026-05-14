package com.re_form_shop_2605.dto.payment;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-08
 * 설명: 결제 취소 요청 DTO (프론트 → 백)
 * ─────────────────────────────────────────────────────
 */

public record PaymentCancelRequestDTO(
        String cancelReason, // 취소 사유
        String cancelType	 // CANCEL or REFUND
) { }