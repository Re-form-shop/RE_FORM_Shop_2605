package com.re_form_shop_2605.dto.admin;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-23
 * 설명: 관리자 대시보드 요약 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminDashboardSummaryDTO(
        // 전체 회원 수
        long memberCount,
        // 처리 대기 신고 수
        long pendingReportCount,
        // 처리 대기 출금 수
        long pendingWithdrawCount,
        // 분쟁 거래 수
        long disputeCount,
        // 오늘 생성 거래 수
        long todayTradeCount,
        // 오늘 완료 거래 수
        long todayCompletedTradeCount,
        // 오늘 취소 거래 수
        long todayCanceledTradeCount,
        // 오늘 거래 총액
        long todayTradeVolume,
        // 최근 거래 목록
        List<AdminTradeSummaryDTO> recentTrades
) {
}
