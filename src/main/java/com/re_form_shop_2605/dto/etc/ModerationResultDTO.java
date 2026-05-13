package com.re_form_shop_2605.dto.etc;

import com.re_form_shop_2605.entity.Enum.RiskLevel;

public record ModerationResultDTO(
        boolean flagged,
        RiskLevel riskLevel,
        String reason,
        String suggestion
) {}
