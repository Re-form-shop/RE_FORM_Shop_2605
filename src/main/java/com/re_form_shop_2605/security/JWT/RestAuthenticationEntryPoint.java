package com.re_form_shop_2605.security.JWT;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
/**
 * 작성자: 민기
 * 작성일: 2026-05-11
 * 설명:
 */
// 인증이 안 된 요청을 프론트가 바로 처리할 수 있는 JSON 401 응답으로 바꾸는 진입점
@Log4j2
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        // 보호된 API에 토큰 없이 접근한 경우 인증 정책에 맞춰 JSON 403 응답을 내려준다.
        log.warn("[RestAuthenticationEntryPoint] unauthorized path={} message={}", request.getRequestURI(), authException.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":false,\"message\":\"인증이 필요합니다.\",\"data\":{\"message\":\"Forbidden\"}}");
    }
}
