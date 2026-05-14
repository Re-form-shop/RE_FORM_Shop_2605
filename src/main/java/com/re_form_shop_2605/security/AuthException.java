package com.re_form_shop_2605.security;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 인증 처리에서 사용하는 사용자 정의 예외
 * ─────────────────────────────────────────────────────
 */
// 로그인/토큰/세션 검증 실패처럼 인증 도메인에서 의도적으로 구분할 예외 타입
public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }
}
