package com.re_form_shop_2605.dto.login;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 로그인 성공 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record LoginResponseDTO(
        // JWT 액세스 토큰
        String accessToken,
        // 리프레시 토큰
        String refreshToken,
        // 로그인한 사용자 정보
        AuthUserDTO user
) {}
