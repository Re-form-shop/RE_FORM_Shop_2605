package com.re_form_shop_2605.dto.admin;

import com.re_form_shop_2605.entity.Enum.TradeDeliveryType;
import com.re_form_shop_2605.entity.Enum.TradeStatus;

import java.time.LocalDateTime;

/**
 * 설명: 관리자 분쟁 목록 DTO
 */
public record AdminDisputeListDTO(
        Long tradeId,
        Long postId,
        String postTitle,
        int price,
        TradeStatus status,
        TradeDeliveryType deliveryType,
        LocalDateTime createdAt,
        Long buyerMemberId,
        String buyerNickname,
        Long sellerMemberId,
        String sellerNickname
) {
}
