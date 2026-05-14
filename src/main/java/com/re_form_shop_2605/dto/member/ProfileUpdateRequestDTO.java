package com.re_form_shop_2605.dto.member;

import jakarta.validation.constraints.Size;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 내 프로필 수정 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record ProfileUpdateRequestDTO(
        // 변경할 닉네임
        @Size(min = 2, max = 20)
        String nickname,

        // 변경할 프로필 이미지 URL
        @Size(max = 500)
        String profileImageUrl,

        // 변경할 자기소개
        @Size(max = 200)
        String bio
) {
}
