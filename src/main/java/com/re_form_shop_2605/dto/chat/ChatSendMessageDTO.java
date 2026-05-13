package com.re_form_shop_2605.dto.chat;

public record ChatSendMessageDTO(
        Long chatId, // 어느 채팅방인지
        Long senderId, // 보내는 사람 ID
        String content, // 내용
        String type // "TEXT" | "IMAGE" | "SYSTEM"
) {}
