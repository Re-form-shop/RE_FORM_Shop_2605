package com.re_form_shop_2605.dto.community;

import jakarta.validation.constraints.Size;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: 커뮤니티 게시글 수정용 DTO
 * ─────────────────────────────────────────────────────
 */
// PUT /api/community/{commId}
public record CommunityPostUpdateRequestDTO(
        @Size(max = 200)
        String commTitle,

        String commContent,

        @Size(max = 500)
        String commImageUrl
) {}
