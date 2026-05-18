package com.re_form_shop_2605.domain.etc;

import com.re_form_shop_2605.entity.Enum.RiskLevel;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-13
 * 설명: 유해성 검사 탐지 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RiskAnalysisResultVO {
    private Long riskId;
    private String targetType; // ENUM ('POST', 'CHAT') 감지된 대상 분류
    private Long targetId; // 대상 ID
    private RiskLevel riskLevel; //  ENUM ('LOW', 'MID', 'HIGH') 위험 레벨
    private String reason; // 탐지 사유
    private String suggestion; // 개선 제안
    private LocalDateTime createdAt;
}
