package com.re_form_shop_2605.dto.statistics;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-27
 * 설명: 메인화면 통계 조회 응답 DTO
 * ─────────────────────────────────────────────────────
 */

public record StatisticsResponseDTO (
        long tradeCount,    // 누적 거래 수
        long productCount,  // 등록 상품 수
        long memberCount,   // 활성 회원 수
        long satisfaction // 만족도(%)
) {}