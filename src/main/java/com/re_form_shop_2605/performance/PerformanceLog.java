package com.re_form_shop_2605.performance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-14
 * 설명: 성능 모니터링을 위한 AOP entity 클래스
 * ─────────────────────────────────────────────────────
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mid;
    private String action;
    private LocalDateTime createdAt;
}
