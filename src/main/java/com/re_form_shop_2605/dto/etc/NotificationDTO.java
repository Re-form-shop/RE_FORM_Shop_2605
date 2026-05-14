package com.re_form_shop_2605.dto.etc;

import com.re_form_shop_2605.entity.Enum.NotificationType;

import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 알림 단건 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record NotificationDTO(
        // 알림 번호
        Long notiId,
        // 알림 타입
        NotificationType type,
        // 알림 내용
        String content,
        // 클릭 시 이동 경로
        String linkUrl,
        // 읽음 여부
        boolean isRead,
        // 생성일
        LocalDateTime createdAt
) {
}
