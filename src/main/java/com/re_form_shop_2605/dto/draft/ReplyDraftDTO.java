package com.re_form_shop_2605.dto.draft;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 댓글 작성 중 임시 저장할 초안 DTO
 * ─────────────────────────────────────────────────────
 */
public record ReplyDraftDTO(
        // 댓글이 달리는 대상 종류
        @NotBlank
        String targetType,
        // 댓글이 달리는 대상 ID
        @NotNull
        Long targetId,
        // 댓글 본문
        @Size(max = 2000)
        String content
) {
}
