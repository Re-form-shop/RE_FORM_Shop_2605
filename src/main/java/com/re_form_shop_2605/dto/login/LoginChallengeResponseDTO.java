package com.re_form_shop_2605.dto.login;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-13
 * 설명: 이메일 2차 인증 대기 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record LoginChallengeResponseDTO(
        String challengeId,
        String email,
        long expiresInSeconds
) {
}
