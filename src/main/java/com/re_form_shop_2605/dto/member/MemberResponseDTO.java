package com.re_form_shop_2605.dto.member;

import com.re_form_shop_2605.dto.login.AuthUserDTO;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 회원가입 성공 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record MemberResponseDTO(
        // JWT 액세스 토큰
        String accessToken,
        // 리프레시 토큰
        String refreshToken,
        // 가입 직후 로그인된 사용자 정보
        AuthUserDTO user
) {
}
