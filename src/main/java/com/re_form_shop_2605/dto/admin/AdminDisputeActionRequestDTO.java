package com.re_form_shop_2605.dto.admin;

import jakarta.validation.constraints.NotNull;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-24
 * 설명: 관리자 분쟁 처리 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminDisputeActionRequestDTO(
        // 처리 액션
        @NotNull
        AdminDisputeAction action,
        // 관리자 처리 메모
        String adminMemo,
        // 연장 일수
        Integer extensionDays
) {
}
