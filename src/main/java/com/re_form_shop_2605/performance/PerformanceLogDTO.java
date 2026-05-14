package com.re_form_shop_2605.performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-14
 * 설명: 성능 모니터링을 위한 DTO 클래스
 * ─────────────────────────────────────────────────────
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceLogDTO {
    private String className; // 클래스 이름
    private String methodName; // 메서드 이름
    private long executionTime; // 실행 시간 (ms)
    private long createdAt; // 실행 로그 생성 시간 (타임스탬프)
}
