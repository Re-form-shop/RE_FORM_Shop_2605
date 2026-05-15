package com.re_form_shop_2605.domain.chat;

import lombok.*;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-08
 * 설명: 채팅방 VO
 * ─────────────────────────────────────────────────────
 */
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
