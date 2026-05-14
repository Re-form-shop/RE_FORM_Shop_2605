package com.re_form_shop_2605.dto.trade;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-14
 * 설명: AI가 이미지를 분석해 제안하는 판매글 제목/설명 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record AiListingSuggestResponseDTO(
        String title,
        String description
) {
    public static AiListingSuggestResponseDTO fallback() {
        return new AiListingSuggestResponseDTO("", "");
    }
}
