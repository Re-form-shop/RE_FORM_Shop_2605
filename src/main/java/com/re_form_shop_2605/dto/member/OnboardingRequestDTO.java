package com.re_form_shop_2605.dto.member;

import com.re_form_shop_2605.entity.Enum.Sport;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 회원가입 직후 관심 정보 저장/수정 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record OnboardingRequestDTO(
        // 관심 종목
        @NotNull
        Sport sport,

        // 관심 구단명
        @Size(max = 100)
        String team,

        // 관심 키워드 목록
        @Size(max = 10)
        List<@NotBlank @Size(max = 200) String> keywords
) {
}
