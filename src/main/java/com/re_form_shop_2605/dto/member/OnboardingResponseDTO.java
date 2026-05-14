package com.re_form_shop_2605.dto.member;

import com.re_form_shop_2605.entity.Enum.Sport;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 온보딩 관심 정보 조회/저장 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record OnboardingResponseDTO(
        // 관심 종목
        Sport sport,
        // 관심 구단명
        String team,
        // 관심 키워드 목록
        List<String> keywords
) {
}
