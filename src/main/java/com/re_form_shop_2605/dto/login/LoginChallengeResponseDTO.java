package com.re_form_shop_2605.dto.login;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-13
 * 설명: 이메일 2차 인증 대기 응답 DTO
 * verificationCode: 개발 편의용 — 로그인 UI에 인증코드 직접 표시
 * ─────────────────────────────────────────────────────
 */
public record LoginChallengeResponseDTO(
        String challengeId,
        String email,
        long expiresInSeconds,
        String verificationCode   // 개발 편의용 코드 노출 (프론트 DEV 힌트 박스에 표시됨)
) {
}
