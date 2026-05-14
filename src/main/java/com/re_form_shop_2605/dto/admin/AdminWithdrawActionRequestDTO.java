package com.re_form_shop_2605.dto.admin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관리자 출금 요청 처리 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminWithdrawActionRequestDTO(
        // 수행할 출금 처리 액션
        @NotNull
        WithdrawAction action,

        // 반려 사유
        @Size(max = 300)
        String rejectReason
) {
}
