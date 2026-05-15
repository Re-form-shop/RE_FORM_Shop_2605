package com.re_form_shop_2605.dto.community;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: 커뮤니티 게시글 조회용 DTO
 * ─────────────────────────────────────────────────────
 */
// POST /api/community, PUT /api/community/{commId} 응답
public record CommIdResponseDTO(
   Long commId
) {}
