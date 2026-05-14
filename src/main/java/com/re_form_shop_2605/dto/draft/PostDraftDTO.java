package com.re_form_shop_2605.dto.draft;

import jakarta.validation.constraints.Size;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 게시글 작성 중 임시 저장할 초안 DTO
 * ─────────────────────────────────────────────────────
 */
public record PostDraftDTO(
        // 게시글 제목
        @Size(max = 200)
        String title,
        // 게시글 본문
        @Size(max = 2000)
        String content
) {
}
