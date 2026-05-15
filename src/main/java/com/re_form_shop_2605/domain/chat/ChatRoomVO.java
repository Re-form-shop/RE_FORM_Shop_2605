package com.re_form_shop_2605.domain.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomVO {
    private Long chatId;
    private Long tradeId;
    private Long buyerId;
    private Long sellerId;
    private LocalDateTime createdAt;
}
