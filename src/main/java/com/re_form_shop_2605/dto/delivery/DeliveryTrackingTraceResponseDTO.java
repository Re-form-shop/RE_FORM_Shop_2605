package com.re_form_shop_2605.dto.delivery;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 송장 조회 API 응답을 담는 DTO
 * ─────────────────────────────────────────────────────
 */
public record DeliveryTrackingTraceResponseDTO(
        boolean isSuccess,
        TraceResponseData data
) {
    public record TraceResponseData(
            List<TraceResultDTO> results,
            TraceSummaryDTO summary
    ) {
    }

    public record TraceResultDTO(
            String clientId,
            boolean success,
            TrackingDataDTO data,
            TraceErrorDTO error,
            TraceCacheDTO cache
    ) {
    }

    public record TrackingDataDTO(
            String trackingNumber,
            String courierCode,
            String courierName,
            String deliveryStatus,
            String deliveryStatusText,
            boolean isDelivered,
            String senderName,
            String receiverName,
            String productName,
            String arrivalBranch,
            String dateLastProgress,
            List<TrackingProgressDTO> progresses,
            String queriedAt
    ) {
    }

    public record TrackingProgressDTO(
            String dateTime,
            String location,
            String status,
            String statusCode,
            String description
    ) {
    }

    public record TraceErrorDTO(
            String code,
            String message,
            String courierCode,
            String trackingNumber,
            Boolean billable
    ) {
    }

    public record TraceCacheDTO(
            boolean fromCache,
            String cachedAt
    ) {
    }

    public record TraceSummaryDTO(
            int total,
            int successful,
            int failed,
            int billable
    ) {
    }
}
