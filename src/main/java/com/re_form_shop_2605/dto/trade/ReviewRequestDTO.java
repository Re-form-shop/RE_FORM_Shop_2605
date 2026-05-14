package com.re_form_shop_2605.dto.trade;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 매너 평가 작성 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record ReviewRequestDTO(
        // 대상 거래 번호
        @NotNull
        Long tradeId,

        // 별점
        @NotNull @Min(1) @Max(5)
        Integer score,

        // 후기 본문
        @Size(max = 500)
        String content
) {
}
