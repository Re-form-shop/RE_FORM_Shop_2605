package com.re_form_shop_2605.entity.payment;

import com.re_form_shop_2605.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-07
 * 설명: 토스 API 응답 로그 Entity
 *      - 결제 승인 시 토스 API 원문 응답 저장
 *      - append-only (수정 없이 이력만 쌓음)
 * ─────────────────────────────────────────────────────
 */

@Entity
@Table(name = "toss_log")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TossLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId; // 로그 번호

    @ManyToOne(fetch = FetchType.LAZY) // append-only
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment; // 결제 번호(FK)

    @Column(name = "toss_payment_key", length = 200)
    private String tossPaymentKey; // Toss 결제 키

    @Column(name = "raw_response", columnDefinition = "JSON")
    private String rawResponse; // 토스 API 원문
}