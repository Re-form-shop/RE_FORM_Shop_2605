package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import com.re_form_shop_2605.entity.Enum.Sport;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 판매글 수정 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record PostUpdateRequestDTO(
        // 수정할 제목
        @Size(max = 200)
        String title,

        // 수정할 본문
        String content,

        // 수정할 종목
        Sport sport,

        // 수정할 팀명
        @Size(max = 50)
        String team,

        // 수정할 유니폼명
        @Size(max = 200)
        String uniformName,

        // 수정할 유니폼 등급
        Grade grade,

        // 수정할 유니폼 사이즈
        @Size(max = 10)
        String size,

        // 수정할 마킹 여부
        Boolean marking,

        // 수정할 가격
        @Positive
        Integer price,

        // 수정할 수령 방법
        DeliveryType deliveryType
) {
}
