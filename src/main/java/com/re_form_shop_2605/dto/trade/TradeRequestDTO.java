package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.entity.Enum.TradeDeliveryType;
import jakarta.validation.constraints.NotNull;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 거래 생성 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record TradeRequestDTO(
        // 거래를 생성할 판매글 번호
        @NotNull
        Long postId,
        // 구매자가 선택한 수령 방식 (양쪽 모두 가능할 때만 필수)
        TradeDeliveryType deliveryType
) {
}
