package com.re_form_shop_2605.dto.trade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 배송지 입력 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record DeliveryRequestDTO(
        // 배송 주소
        @NotBlank
        @Size(max = 300)
        String deliveryAddress
) {
}
