package com.re_form_shop_2605.security.handler;

import com.re_form_shop_2605.dto.login.LoginResponseDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.service.login.AuthTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 소셜 로그인 성공 후 토큰 발급과 리다이렉트를 처리하는 핸들러
 * ─────────────────────────────────────────────────────
 */
@Slf4j
// OAuth2 로그인 성공 시 자체 JWT를 발급해 프론트 SPA 콜백으로 전달하는 성공 핸들러
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthTokenService authTokenService;
    private final String oauthSuccessRedirectUri;

    public CustomSocialLoginSuccessHandler(AuthTokenService authTokenService, String oauthSuccessRedirectUri) {
        // 설정과 토큰 서비스는 config에서 조립해 전달받고, 이 클래스는 성공 후 후처리만 담당한다.
        this.authTokenService = authTokenService;
        this.oauthSuccessRedirectUri = oauthSuccessRedirectUri;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        // 소셜 로그인 성공 직후에는 OAuth 제공자 인증을 신뢰하고 JWT를 발급해 프론트 콜백으로 넘긴다.
        MemberSecurityDTO principal = (MemberSecurityDTO) authentication.getPrincipal();
        log.info("[CustomSocialLoginSuccessHandler] success start memberId={} provider={}",
                principal.getMemberId(),
                principal.getLoginProvider());
        LoginResponseDTO loginResponseDTO = authTokenService.issueTokens(principal);

        String redirectUrl = oauthSuccessRedirectUri
                + "#accessToken=" + URLEncoder.encode(loginResponseDTO.accessToken(), StandardCharsets.UTF_8)
                + "&refreshToken=" + URLEncoder.encode(loginResponseDTO.refreshToken(), StandardCharsets.UTF_8);

        // 토큰은 쿼리스트링 대신 fragment로 전달해 서버 로그/프록시 노출을 상대적으로 줄인다.
        log.info("[CustomSocialLoginSuccessHandler] success end memberId={} redirectUri={}",
                principal.getMemberId(),
                oauthSuccessRedirectUri);
        response.sendRedirect(redirectUrl);
    }
}
