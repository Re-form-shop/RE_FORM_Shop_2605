package com.re_form_shop_2605.repository.payment;

import com.re_form_shop_2605.entity.payment.TossLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-
 * 설명: 토스 API 응답 로그 Repository
 *       - 토스 결제 승인 시 원문 응답 저장 용도
 * ─────────────────────────────────────────────────────
 */

@Repository
public interface TossLogRepository extends JpaRepository<TossLog, Long> {
    /* 토스 로그 저장 */
}
