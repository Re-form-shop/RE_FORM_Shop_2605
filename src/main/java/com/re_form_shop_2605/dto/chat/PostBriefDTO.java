package com.re_form_shop_2605.dto.chat;

import com.re_form_shop_2605.entity.Enum.PostStatus;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-09
 * 설명: 판매글 요약 DTO
 * ─────────────────────────────────────────────────────
 */
// GET /api/chats
public record PostBriefDTO(
        Long postId,
        String title,
        String thumbnailUrl,
        int price,
        PostStatus status
) {}