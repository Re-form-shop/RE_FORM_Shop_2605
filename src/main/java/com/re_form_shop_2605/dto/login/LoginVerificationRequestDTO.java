package com.re_form_shop_2605.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-13
 * 설명: 이메일 2차 인증 코드 검증 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record LoginVerificationRequestDTO(
        @NotBlank
        String challengeId,

        @NotBlank
        @Pattern(regexp = "\\d{6}", message = "인증코드는 6자리 숫자여야 합니다.")
        String code
) {
}
