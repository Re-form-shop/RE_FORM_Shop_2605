package com.re_form_shop_2605.dto.login;

import jakarta.validation.constraints.NotBlank;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 로그아웃 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record LogoutRequestDTO(
        @NotBlank
        String refreshToken
) {
}
