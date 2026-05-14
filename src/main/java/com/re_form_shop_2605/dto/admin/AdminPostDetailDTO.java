package com.re_form_shop_2605.dto.admin;

import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.Sport;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 관리자 게시글 상세 조회에 사용하는 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminPostDetailDTO(
        Long postId,
        String title,
        String content,
        Sport sport,
        String team,
        String uniformName,
        Grade grade,
        String size,
        Boolean marking,
        int price,
        DeliveryType deliveryType,
        PostStatus status,
        int viewCount,
        int wishCount,
        long reportCount,
        List<String> imageUrls,
        Long sellerId,
        String sellerNickname,
        String sellerEmail,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long tradeId
) {
}
