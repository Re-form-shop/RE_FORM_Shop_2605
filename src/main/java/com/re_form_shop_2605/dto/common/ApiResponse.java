package com.re_form_shop_2605.dto.common;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 프론트와 백엔드가 공통으로 사용하는 래핑 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    // 성공 응답을 생성한다.
    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(true, message, data);
    }

    // 실패 응답을 생성한다.
    public static <T> ApiResponse<T> fail(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }
}
