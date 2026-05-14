package com.re_form_shop_2605.repository.payment;

import com.re_form_shop_2605.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-08
 * 설명: 결제 저장 및 조회 Repository
 * ─────────────────────────────────────────────────────
 */

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    /* 결제 저장 및 조회 */
    // 1. 주문번호 조회
    Optional<Payment> findByTossOrderId(String tossOrderId);

    // 2. 결제 정보 조회
    Optional<Payment> findByTossPaymentKey(String tossPaymentKey);

    // 3. tradeId로 결제 정보 조회
    Optional<Payment> findByTradeTradeId(Long tradeId);
}
