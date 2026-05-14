package com.re_form_shop_2605.dto.admin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 관리자 게시글 숨김/삭제 처리 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminPostRequestDTO(
        @NotNull
        PostAction action,

        @Size(max = 300)
        String reason
) {
}
