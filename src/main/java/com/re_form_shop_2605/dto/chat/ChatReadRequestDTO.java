package com.re_form_shop_2605.dto.chat;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-18
 * 설명: 채팅방 읽음 처리용 DTO
 * ─────────────────────────────────────────────────────
 */
public record ChatReadRequestDTO(
        Long chatId, // 읽음 처리할 채팅방 ID
        Long myId // 읽음 처리를 요청한 사용자 ID
) {}
