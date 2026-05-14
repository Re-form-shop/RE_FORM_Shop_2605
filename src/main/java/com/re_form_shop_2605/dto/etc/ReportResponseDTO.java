package com.re_form_shop_2605.dto.etc;

import com.re_form_shop_2605.entity.Enum.ReportReason;
import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.entity.Enum.ReportTargetType;

import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 신고 조회 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record ReportResponseDTO(
        // 신고 번호
        Long reportId,
        // 신고 대상 타입
        ReportTargetType targetType,
        // 신고 대상 번호
        Long targetId,
        // 신고 사유
        ReportReason reason,
        // 상세 설명
        String detail,
        // 처리 상태
        ReportStatus status,
        // 생성일
        LocalDateTime createdAt
) {
}
