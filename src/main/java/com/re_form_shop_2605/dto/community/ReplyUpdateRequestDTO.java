package com.re_form_shop_2605.dto.community;

import jakarta.validation.constraints.NotBlank;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-15
 * 설명: 커뮤니티 게시글 댓글 수정용 DTO
 * ─────────────────────────────────────────────────────
 */
// PUT /api/community/replies/{replyId}
public class ReplyUpdateRequestDTO {
    @NotBlank
    String replyContent;
}
