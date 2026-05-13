package com.re_form_shop_2605.entity.Enum;

import java.util.Arrays;
import java.util.Optional;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-13
 * 설명: 사용 문구 위험 등급(RiskLevel)지정 Enum
 * ─────────────────────────────────────────────────────
 */
public enum ModerationCategory {
    /*
    * message : 프론트에 전달할 한국어 사유 문구

    * riskLevel  : 해당 카테고리의 위험 등급
    * - HIGH  : 즉각 차단이 필요한 심각한 위반
    * - MID   : 주의가 필요한 중간 수준 위반
    * - LOW   : 경미한 수준의 위반
     */

    /* HIGH 등급 (즉각 차단이 필요한 카테고리) */
    HATE_THREATENING(
            "hateThreatening",
            "혐오 표현에 폭력이나 위협이 결합된 표현",
            RiskLevel.HIGH
    ),
    HARASSMENT_THREATENING(
            "harassmentThreatening",
            "위협적인 표현",
            RiskLevel.HIGH
    ),
    SELF_HARM_INSTRUCTIONS(
            "selfHarmInstructions",
            "자해 방법을 안내하는 표현",
            RiskLevel.HIGH
    ),
    SEXUAL_MINORS(
            "sexualMinors",
            "미성년자 관련 성적 콘텐츠",
            RiskLevel.HIGH
    ),
    ILLICIT_VIOLENT(
            "illicitViolent",
            "폭력적 불법 행위",
            RiskLevel.HIGH
    ),
    VIOLENCE_GRAPHIC(
            "violenceGraphic",
            "잔혹한 폭력 묘사",
            RiskLevel.HIGH
    ),

    /* MID 등급 (주의가 필요한 카테고리) */
    HATE(
            "hate",
            "특정 집단이나 개인을 차별·비하하는 표현",
            RiskLevel.MID
    ),
    HARASSMENT(
            "harassment",
            "개인에 대한 공격적 표현",
            RiskLevel.MID
    ),
    SELF_HARM(
            "selfHarm",
            "자해나 자살과 관련된 표현",
            RiskLevel.MID
    ),
    SELF_HARM_INTENT(
            "selfHarmIntent",
            "자해나 자살 의도를 포함한 표현",
            RiskLevel.MID
    ),
    VIOLENCE(
            "violence",
            "폭력 표현",
            RiskLevel.MID
    ),
    ILLICIT(
            "illicit",
            "불법 행위",
            RiskLevel.MID
    ),

    /* LOW 등급 (경미한 수준의 위반) */
    SEXUAL(
            "sexual",
            "성적 콘텐츠",
            RiskLevel.LOW
    );

    private final String code;
    private final String message;
    private final RiskLevel riskLevel;

    ModerationCategory(String code, String message, RiskLevel riskLevel) {
        this.code = code;
        this.message = message;
        this.riskLevel = riskLevel;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public static Optional<ModerationCategory> fromCode(String code) {
        return Arrays.stream(values())
                .filter(c -> c.code.equals(code))
                .findFirst();
    }
}
