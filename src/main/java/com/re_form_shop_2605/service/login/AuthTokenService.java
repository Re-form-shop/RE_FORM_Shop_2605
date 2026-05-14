package com.re_form_shop_2605.service.login;

import com.re_form_shop_2605.dto.login.LoginResponseDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 인증 토큰 저장 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface AuthTokenService {

    // 로그인 마다 토큰 발급
    LoginResponseDTO issueTokens(MemberSecurityDTO principal);

    // 일반 로그인과 소셜 로그인 토큰 발급 규칙 정의
    LoginResponseDTO issueTokens(MemberSecurityDTO principal, String sessionId);

    // 액세스 토큰을 블랙리스트에 등록해 즉시 무효화한다.
    void blacklistAccessToken(String accessToken);

    // 해당 액세스 토큰이 블랙리스트에 있는지 확인한다.
    boolean isAccessTokenBlacklisted(String accessToken);
}
