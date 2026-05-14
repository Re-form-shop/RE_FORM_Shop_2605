package com.re_form_shop_2605.dto.admin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관리자 회원 제재 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminMemberRequestDTO(
        // 수행할 제재 액션
        @NotNull
        MemberAction action,

        // 제재 사유
        @Size(max = 300)
        String reason
) {
}
