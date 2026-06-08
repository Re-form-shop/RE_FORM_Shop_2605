package com.re_form_shop_2605.service.admin;

import com.re_form_shop_2605.dto.admin.AdminDashboardSummaryDTO;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-23
 * 설명: 관리자 대시보드 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface AdminDashboardService {

    // 관리자 대시보드 요약 정보를 조회
    AdminDashboardSummaryDTO readSummary();
}
