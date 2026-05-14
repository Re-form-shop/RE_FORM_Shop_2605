package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.entity.Enum.TradeStatus;
import jakarta.validation.constraints.NotNull;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 거래 상태 변경 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record TradeStatusRequestDTO(
        // 변경할 거래 상태
        @NotNull
        TradeStatus status
) {
}
