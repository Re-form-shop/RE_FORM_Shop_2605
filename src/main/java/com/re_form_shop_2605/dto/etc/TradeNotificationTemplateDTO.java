package com.re_form_shop_2605.dto.etc;

import com.re_form_shop_2605.entity.Enum.NotificationType;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-13
 * 설명: 거래 알림과 이메일에서 공통으로 재사용할 DTO
 * ─────────────────────────────────────────────────────
 */
public record TradeNotificationTemplateDTO(
        NotificationType type,
        String emailSubject,
        String content,
        String linkUrl
) {
}
