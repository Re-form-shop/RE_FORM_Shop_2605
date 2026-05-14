package com.re_form_shop_2605.dto.etc;

import com.re_form_shop_2605.dto.common.PageResponse;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 알림 목록 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record NotificationResponseDTO(
        // 알림 목록
        PageResponse<NotificationDTO> items,
        // 읽지 않은 알림 개수
        int unreadCount
) {
}
