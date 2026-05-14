package com.re_form_shop_2605.dto.admin;

import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.Sport;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 관리자 게시글 목록 조회에 사용하는 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminPostListDTO(
        Long postId,
        String title,
        Sport sport,
        String team,
        int price,
        PostStatus status,
        long reportCount,
        Long sellerId,
        String sellerNickname,
        LocalDateTime createdAt
) {
}
