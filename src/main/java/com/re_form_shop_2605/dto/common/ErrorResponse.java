package com.re_form_shop_2605.dto.common;


import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 공통으로 사용하는 에러 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record ErrorResponse(
        String code, String message, LocalDateTime timestamp
) {
}
