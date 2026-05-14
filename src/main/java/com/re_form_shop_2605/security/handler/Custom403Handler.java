package com.re_form_shop_2605.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 접근 거부 상황을 403 응답으로 처리하는 핸들러
 * ─────────────────────────────────────────────────────
 */
@Log4j2
public class Custom403Handler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 레거시 폼 요청에서 접근 권한이 없을 때는 로그인 페이지로 보내는 보조 핸들러
        log.info("-----------Access Denied---------------");

        response.setStatus(HttpStatus.FORBIDDEN.value()); // 응답 코드에 403

        // JSON (ajax) 요청이었는지 확인
        String contentType = request.getHeader("Content-Type");
        boolean isJsonRequest = false;
        if(contentType != null) {
            isJsonRequest = contentType.startsWith("application/json");
        }
        log.info("isJson: {}", isJsonRequest);

        // 일반 request
        // <form> 방식으로 데이터가 처리되는 경우 로그인 페이지로 리다이렉트
        if(!isJsonRequest) {
            // 로그인 페이지로 보냄
            response.sendRedirect("/member/login?error=ACESS_DENIED");
        }


    }
}
