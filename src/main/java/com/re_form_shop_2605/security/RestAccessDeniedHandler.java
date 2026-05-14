package com.re_form_shop_2605.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 인증은 되었지만 권한이 부족한 요청을 프론트 친화적인 JSON 403 응답으로 바꾸는 핸들러
 * ─────────────────────────────────────────────────────
 */
// 인증은 되었지만 권한이 부족한 요청을 프론트 친화적인 JSON 403 응답으로 바꾸는 핸들러
@Log4j2
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        // 인증은 되었지만 권한이 부족한 경우 JSON 형태의 403 응답을 내려준다.
        log.warn("[RestAccessDeniedHandler] forbidden path={} message={}", request.getRequestURI(), accessDeniedException.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":false,\"message\":\"접근 권한이 없습니다.\",\"data\":{\"message\":\"Forbidden\"}}");
    }
}
