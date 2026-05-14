package com.re_form_shop_2605.dto.login;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 액세스 토큰 재발급 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record TokenRefreshResponseDTO(
        // 새 액세스 토큰
        String accessToken,
        // 회전된 리프레시 토큰
        String refreshToken
) {
}
