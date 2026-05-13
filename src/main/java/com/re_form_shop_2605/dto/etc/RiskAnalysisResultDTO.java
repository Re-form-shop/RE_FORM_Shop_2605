package com.re_form_shop_2605.dto.etc;

import com.re_form_shop_2605.entity.Enum.RiskLevel;
import com.re_form_shop_2605.entity.Enum.TargetType;

import java.time.LocalDateTime;

public record RiskAnalysisResultDTO(
        Long riskId,
        TargetType targetType, // POST / CHAT
        Long targetId, // 대상 (messageId or postId)
        RiskLevel riskLevel, // 위험 등급
        String reason, // 감지 사유
        String suggestion, // 개선 제안
        LocalDateTime createdAt // 감지 일시
) {}
