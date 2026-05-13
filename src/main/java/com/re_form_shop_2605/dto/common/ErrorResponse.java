package com.re_form_shop_2605.dto.common;


import java.time.LocalDateTime;
/**
 * 작성자: 민기
 * 작성일: 2026-05-08
 * 설명:
 */
// 에러
public record ErrorResponse(
        String code, String message, LocalDateTime timestamp
) {
}
