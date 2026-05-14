package com.re_form_shop_2605.dto.login;

import java.time.Instant;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 현재 로그인한 사용자의 개별 세션 정보를 반환하는 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record AuthSessionResponseDTO(
        // refresh/access token claim에 공통으로 담기는 세션 식별자
        String sessionId,
        // local / social 같은 상위 로그인 타입
        String loginType,
        // local / kakao / google 같은 실제 로그인 제공자
        String provider,
        // refresh token 발급 시각
        Instant issuedAt,
        // refresh token 만료 시각
        Instant expiresAt,
        // 현재 요청을 보낸 access token과 같은 세션인지 여부
        boolean current
) {
}
