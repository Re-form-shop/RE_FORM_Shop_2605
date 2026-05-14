package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 판매글 수정 요청 DTO
 * ─────────────────────────────────────────────────────
 */
// 프론트엔드 API 명세에 맞춘 판매글 수정 요청 DTO
public record ListingUpdateRequestDTO(
        @Size(max = 200)
        String title,

        String description,

        Grade condition,

        @Size(max = 10)
        String size,

        @Positive
        Integer price,

        DeliveryType tradeType,

        @Size(max = 10)
        List<@NotBlank String> imageUrls
) {
    // 프론트 요청 필드를 현재 도메인 수정 DTO로 맞춘다.
    public PostUpdateRequestDTO toPostUpdateRequestDTO() {
        return new PostUpdateRequestDTO(
                title,
                description,
                condition,
                size,
                null,
                price,
                tradeType
        );
    }
}
