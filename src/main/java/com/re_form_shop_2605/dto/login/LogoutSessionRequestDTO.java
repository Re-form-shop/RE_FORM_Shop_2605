package com.re_form_shop_2605.dto.login;

import jakarta.validation.constraints.NotBlank;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 현재 사용자 계정에 연결된 특정 세션 하나만 로그아웃할 때 사용하는 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record LogoutSessionRequestDTO(
        @NotBlank
        String sessionId
) {
}
