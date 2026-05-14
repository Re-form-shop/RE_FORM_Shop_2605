package com.re_form_shop_2605.performance;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-14
 * 설명: 성능 모니터링을 위한 Repository 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface PerformanceRepository extends JpaRepository<PerformanceLog, Long> {
}
