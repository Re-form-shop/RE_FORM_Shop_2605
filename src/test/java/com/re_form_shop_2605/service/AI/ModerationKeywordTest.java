package com.re_form_shop_2605.service.AI;

import com.re_form_shop_2605.entity.Enum.RiskLevel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ModerationKeywordTest {

    private final ModerationKeyword moderationKeyword = new ModerationKeyword();

    @Test
    void detect_marksAdvancePaymentAndBankTransferAsHighRisk() {
        ModerationKeyword.LocalModerationHit hit = moderationKeyword.detect("선불로 계좌이체 부탁드립니다");

        assertThat(hit).isNotNull();
        assertThat(hit.riskLevel()).isEqualTo(RiskLevel.MID);
        assertThat(hit.reason()).contains("사기 위험");
    }

    @Test
    void detect_marksAuthenticityDisclaimerAsMidRiskEvenWithSpaces() {
        ModerationKeyword.LocalModerationHit hit = moderationKeyword.detect("이 상품은 정품 아님 안내드립니다");

        assertThat(hit).isNotNull();
        assertThat(hit.riskLevel()).isEqualTo(RiskLevel.MID);
        assertThat(hit.reason()).contains("주의");
    }
}
