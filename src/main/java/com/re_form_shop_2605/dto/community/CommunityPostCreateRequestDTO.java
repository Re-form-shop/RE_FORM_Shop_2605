package com.re_form_shop_2605.dto.community;


import com.re_form_shop_2605.entity.Enum.Sport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-09
 * 설명: 커뮤니티 게시글 작성 DTO
 * ─────────────────────────────────────────────────────
 */
// POST /api/community
public record CommunityPostCreateRequestDTO(
        @NotNull
        Sport Sport,

        @Size(max = 50)
        String teamCategory, // 관련 구단 (선택)

        @NotBlank @Size(max = 200)
        String commTitle,

        @NotBlank
        String commContent,

        @Size(max = 500)
        String commImageUrl // 첨부 이미지 URL (선택 — 업로드 후 URL 전달)
) {}