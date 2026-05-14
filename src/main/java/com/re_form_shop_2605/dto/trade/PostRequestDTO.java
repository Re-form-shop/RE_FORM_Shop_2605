package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import com.re_form_shop_2605.entity.Enum.Sport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 판매글 등록 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record PostRequestDTO(
        // 제목
        @NotBlank
        @Size(max = 200)
        String title,

        // 본문
        @NotBlank
        String content,

        // 종목
        @NotNull
        Sport sport,

        // 구단명
        @NotBlank
        @Size(max = 50)
        String team,

        // 유니폼명
        @NotBlank
        @Size(max = 200)
        String uniformName,

        // 유니폼 상태 등급
        @NotNull
        Grade grade,

        // 유니폼 사이즈
        @Size(max = 10)
        String size,

        // 마킹 여부
        Boolean marking,

        // 판매 희망가
        @NotNull
        @Positive
        int price,

        // 수령 방법
        @NotNull
        DeliveryType deliveryType
) {
}
