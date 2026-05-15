package com.re_form_shop_2605.dto.community;

import jakarta.validation.constraints.NotBlank;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: 커뮤니티 게시글 댓글 작성용 DTO
 * ─────────────────────────────────────────────────────
 */
// POST /api/community/{commId}/replies
public record ReplyCreateRequestDTO(
        @NotBlank
        String replyContent,
        Long parentId // 대댓글인 경우 부모 replyId, 최상위면 null
) {}
