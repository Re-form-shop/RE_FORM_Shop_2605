package com.re_form_shop_2605.dto.AI;

import com.re_form_shop_2605.entity.Enum.RiskLevel;
import com.re_form_shop_2605.entity.Enum.TargetType;
import com.re_form_shop_2605.entity.AI.RiskAnalysisResult;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-13
 * 설명: 위험 탐지 목록 반환 DTO
 * ─────────────────────────────────────────────────────
 */
public record RiskAnalysisResultDTO(
        Long riskId,
        TargetType targetType,  // POST / CHAT
        Long targetId,          // 대상 (messageId or postId)
        RiskLevel riskLevel,    // 위험 등급 (null = 정상, 값 있음 = 유해)
        String reason,          // 감지 사유 (정상이면 null)
        String suggestion,      // ChatGPT 개선 제안 (정상이면 null)
        LocalDateTime createdAt // 감지 일시
) {
    /* 정상 메시지용 정적 팩토리 메서드 (riskLevel = null -> todo 프론트에서 정상 메시지로 처리) */
    public static RiskAnalysisResultDTO safe() {
        return new RiskAnalysisResultDTO(null, null, null, null, null, null, null);
    }

    /* RiskAnalysisResult 엔티티 -> DTO 변환 */
    public static RiskAnalysisResultDTO from(RiskAnalysisResult riskAnalysisResult) {
        return new RiskAnalysisResultDTO(
                riskAnalysisResult.getRiskId(),
                riskAnalysisResult.getTargetType(),
                riskAnalysisResult.getTargetId(),
                riskAnalysisResult.getRiskLevel(),
                riskAnalysisResult.getReason(),
                riskAnalysisResult.getSuggestion(),
                riskAnalysisResult.getCreatedAt()
        );
    }
}
