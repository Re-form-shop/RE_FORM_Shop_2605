package com.re_form_shop_2605.service.login;

import com.re_form_shop_2605.dto.login.LoginResponseDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
/**
 * 작성자: 민기
 * 작성일: 2026-05-11
 * 설명:
 */
public interface AuthTokenService {

    // 로그인 마다 토큰 발급
    LoginResponseDTO issueTokens(MemberSecurityDTO principal);

    // 일반 로그인과 소셜 로그인 토큰 발급 규칙 정의
    LoginResponseDTO issueTokens(MemberSecurityDTO principal, String sessionId);
}
