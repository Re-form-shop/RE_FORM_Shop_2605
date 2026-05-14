package com.re_form_shop_2605.dto.delivery;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 택배사 목록 조회 API 응답을 담는 DTO
 * ─────────────────────────────────────────────────────
 */
public record DeliveryCourierListResponseDTO(
        boolean isSuccess,
        CourierListData data
) {
    public record CourierListData(
            List<CourierDTO> couriers,
            int total
    ) {
    }

    public record CourierDTO(
            String trackingApiCode,
            String displayName
    ) {
    }
}
