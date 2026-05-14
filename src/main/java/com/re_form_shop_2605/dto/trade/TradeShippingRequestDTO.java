package com.re_form_shop_2605.dto.trade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-13
 * 설명: 판매자가 배송 시작 시 입력하는 택배 정보 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record TradeShippingRequestDTO(
        @NotBlank
        @Size(max = 50)
        String courierCode,

        @NotBlank
        @Size(max = 100)
        String trackingNumber
) {
}
