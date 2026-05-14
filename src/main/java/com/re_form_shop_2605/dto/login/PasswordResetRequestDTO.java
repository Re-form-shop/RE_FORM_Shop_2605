package com.re_form_shop_2605.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 비밀번호 찾기 후 새 비밀번호로 재설정하는 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record PasswordResetRequestDTO(
        @NotBlank
        @Email
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(min = 2, max = 20)
        String nickname,

        @NotBlank
        @Size(min = 8)
        String newPassword
) {
}
