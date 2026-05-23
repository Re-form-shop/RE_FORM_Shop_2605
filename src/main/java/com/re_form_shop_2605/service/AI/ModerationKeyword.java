package com.re_form_shop_2605.service.AI;

import com.re_form_shop_2605.entity.Enum.RiskLevel;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class ModerationKeyword {

    private static final List<LocalModerationRule> LOCAL_MODERATION_RULES = List.of(
            new LocalModerationRule("죽어|뒤져|디져|꺼져", "폭력적이거나 위협적인 표현", RiskLevel.HIGH),
            new LocalModerationRule("느금마|니애미|니엄마|네애미", "가족을 향한 심한 비하 표현", RiskLevel.HIGH),
            new LocalModerationRule("선불|계좌이체", "사기 위험이 높은 거래 유도 표현", RiskLevel.MID),
            new LocalModerationRule("가품|도용", "위조품 판매 또는 권리 침해가 의심되는 표현", RiskLevel.HIGH),
            new LocalModerationRule("해외배송|정품아님", "주의가 필요한 판매 표현", RiskLevel.MID),
            new LocalModerationRule("씨+발|시+발|씹+팔|ㅆㅂ|ㅅㅂ|시바|씨바|쉬발|끼발|씨발|시발", "강한 욕설 또는 공격적 표현", RiskLevel.HIGH),
            new LocalModerationRule("병+신|븅+신|빙+신|병신", "개인에 대한 공격적 표현", RiskLevel.HIGH),
            new LocalModerationRule("개새끼|개색끼|개쉐이|개쉑|새끼", "개인에 대한 공격적 표현", RiskLevel.HIGH),
            new LocalModerationRule("지랄|좆|존나|ㅈㄴ", "비속어 또는 공격적 표현", RiskLevel.HIGH)
    );

    public LocalModerationHit detect(String content) {
        if (content == null || content.isBlank()) {
            return null;
        }

        String normalized = normalize(content);
        if (normalized.isBlank()) {
            return null;
        }

        LocalModerationRule matchedRule = null;
        for (LocalModerationRule rule : LOCAL_MODERATION_RULES) {
            if (rule.pattern().matcher(normalized).find()) {
                if (matchedRule == null || rule.riskLevel().ordinal() > matchedRule.riskLevel().ordinal()) {
                    matchedRule = rule;
                }
            }
        }

        if (matchedRule == null) {
            return null;
        }

        return new LocalModerationHit(matchedRule.riskLevel(), matchedRule.reason(), normalized);
    }

    private String normalize(String content) {
        String normalized = Normalizer.normalize(content, Normalizer.Form.NFKC).toLowerCase();
        normalized = normalized.replaceAll("[^0-9a-z가-힣ㄱ-ㅎㅏ-ㅣ]", "");
        return normalized;
    }

    private record LocalModerationRule(Pattern pattern, String reason, RiskLevel riskLevel) {
        private LocalModerationRule(String regex, String reason, RiskLevel riskLevel) {
            this(Pattern.compile(regex), reason, riskLevel);
        }
    }

    public record LocalModerationHit(RiskLevel riskLevel, String reason, String normalizedContent) {
    }
}
