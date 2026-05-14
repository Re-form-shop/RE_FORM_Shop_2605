package com.re_form_shop_2605.dto.payment;

import com.re_form_shop_2605.entity.Enum.PayMethod;
import org.jetbrains.annotations.NotNull;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-08
 * 설명: 결제 초기화 요청 DTO (프론트 → 백)
 * ─────────────────────────────────────────────────────
 */

public record PaymentInitRequestDTO(
        @NotNull Long tradeId,       // 결제할 거래 ID
        @NotNull PayMethod payMethod // 결제 수단
) {
    /* 결제 요청 DTO (프론트 -> 백) */
}
