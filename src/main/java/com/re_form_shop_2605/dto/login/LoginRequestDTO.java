package com.re_form_shop_2605.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 이메일 로그인 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record LoginRequestDTO(
        // 로그인에 사용할 이메일
        @NotBlank
        @Email
        String email,

        // 로그인 비밀번호
        @NotBlank
        String password
) {
}
