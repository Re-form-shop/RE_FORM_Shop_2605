package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.entity.Enum.TradeDeliveryType;
import com.re_form_shop_2605.entity.Enum.TradeStatus;

import java.time.LocalDateTime;

/**
 * 거래 흐름의 실시간 동기화를 위한 WebSocket payload.
 */
public record TradeRealtimeEventDTO(
        String eventType,
        Long tradeId,
        Long chatId,
        Long postId,
        Long buyerId,
        Long sellerId,
        TradeStatus status,
        TradeDeliveryType deliveryType,
        String deliveryAddress,
        String courierCode,
        String courierName,
        String trackingNumber,
        Integer tradePrice,
        LocalDateTime shippingStartedAt,
        LocalDateTime confirmedAt,
        LocalDateTime completedAt,
        LocalDateTime updatedAt
) {
}
