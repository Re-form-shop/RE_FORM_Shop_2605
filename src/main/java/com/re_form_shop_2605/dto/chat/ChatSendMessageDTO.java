package com.re_form_shop_2605.dto.chat;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-11
 * 설명: 클라이언트에서 서버로 메세지 전송용 DTO
 * ─────────────────────────────────────────────────────
 */
public record ChatSendMessageDTO(
        Long chatId, // 어느 채팅방인지
        Long senderId, // 보내는 사람 ID
        String content, // 내용
        String type // "TEXT" | "IMAGE" | "SYSTEM"
) {}
