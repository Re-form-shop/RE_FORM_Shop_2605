package com.re_form_shop_2605.dto.delivery;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 송장 조회 API 요청 본문을 담는 DTO
 * ─────────────────────────────────────────────────────
 */
public record DeliveryTrackingTraceRequestDTO(
        @NotEmpty
        List<@Valid TraceItemRequestDTO> items
) {
    public record TraceItemRequestDTO(
            @Size(max = 100)
            String clientId,

            @NotBlank
            @Size(max = 50)
            String courierCode,

            @NotBlank
            @Size(max = 100)
            String trackingNumber
    ) {
    }
}
