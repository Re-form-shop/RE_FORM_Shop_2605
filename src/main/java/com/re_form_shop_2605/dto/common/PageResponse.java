package com.re_form_shop_2605.dto.common;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 페이지네이션을 위한 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record PageResponse<T>(
        List<T> content,       // 데이터 목록
        long totalElements,    // 전체 건수
        int totalPages,        // 전체 페이지 수
        int size,              // 페이지 크기
        int number,            // 현재 페이지 번호 (응답은 1부터)
        boolean first,         // 첫 페이지 여부
        boolean last           // 마지막 페이지 여부
) {
}
