package com.re_form_shop_2605.dto.AI;

import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.Sport;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-05
 * 설명: AI 추천 게시글 카드 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record RecommendPostCardDTO(
        Long postId,
        String title,
        String team,
        Sport sport,
        int price,
        Grade grade,
        String size,
        DeliveryType deliveryType,
        PostStatus status,
        int viewCount,
        int wishCount,
        String thumbnailUrl,
        LocalDateTime createdAt,
        String recommendReason
) {}