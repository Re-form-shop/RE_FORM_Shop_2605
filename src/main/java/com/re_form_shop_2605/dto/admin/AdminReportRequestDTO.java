package com.re_form_shop_2605.dto.admin;

import com.re_form_shop_2605.entity.Enum.ReportStatus;
import jakarta.validation.constraints.NotNull;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관리자 신고 처리 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminReportRequestDTO(
        // 적용할 신고 처리 상태
        @NotNull
        ReportStatus action
) {
}
