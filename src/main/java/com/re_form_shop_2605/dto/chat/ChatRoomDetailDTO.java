package com.re_form_shop_2605.dto.chat;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.entity.Enum.TradeStatus;

// GET /api/chats/{chatId}
public record ChatRoomDetailDTO(
        Long chatId,
        MemberBriefDTO buyer,
        MemberBriefDTO seller,
        PostBriefDTO post,
        Long tradeId, // 거래 연결된 경우 (null 가능)
        TradeStatus tradeStatus, // 거래 상태 (null 가능)
        PageResponse<ChatMessageDTO> messages
) {}
