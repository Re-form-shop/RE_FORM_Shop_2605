package com.re_form_shop_2605.dto.admin;

import com.re_form_shop_2605.entity.Enum.ReportReason;
import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.entity.Enum.ReportTargetType;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-23
 * 설명: 관리자 신고 상세 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminReportDetailDTO(
        // 신고 번호
        Long reportId,
        // 신고 대상 타입
        ReportTargetType targetType,
        // 신고 대상 번호
        Long targetId,
        // 신고 사유
        ReportReason reason,
        // 신고 상세 내용
        String detail,
        // 신고 처리 상태
        ReportStatus status,
        // 신고 접수 시각
        LocalDateTime createdAt,
        // 신고자 회원 번호
        Long reporterMemberId,
        // 신고자 닉네임
        String reporterNickname,
        // 신고자 이메일
        String reporterEmail,
        // 대상 작성자 회원 번호
        Long targetOwnerMemberId,
        // 대상 작성자 닉네임
        String targetOwnerNickname,
        // 대상 작성자 이메일
        String targetOwnerEmail,
        // 대상 제목
        String targetTitle,
        // 대상 스냅샷
        String targetSnapshot,
        // 관리자 처리 시각
        LocalDateTime processedAt,
        // 처리 관리자 닉네임
        String processedBy,
        // 관리자 처리 메모
        String adminMemo
) {
}
