package com.re_form_shop_2605.dto.etc;

import com.re_form_shop_2605.entity.Enum.ReportReason;
import com.re_form_shop_2605.entity.Enum.ReportTargetType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 신고 등록 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record ReportRequestDTO(
        // 신고 대상 타입
        @NotNull
        ReportTargetType targetType,

        // 신고 대상 번호
        @NotNull
        Long targetId,

        // 신고 사유
        @NotNull
        ReportReason reason,

        // 상세 설명
        @Size(max = 500)
        String detail
) {
}
