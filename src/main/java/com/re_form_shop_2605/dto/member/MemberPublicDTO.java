package com.re_form_shop_2605.dto.member;

import com.re_form_shop_2605.dto.trade.ReviewResponseDTO;

import java.math.BigDecimal;
import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 타 회원 공개 프로필 조회 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record MemberPublicDTO(
        // 회원 번호
        Long memberId,
        // 닉네임
        String nickname,
        // 프로필 이미지 URL
        String profileImageUrl,
        // 자기소개
        String bio,
        // 매너 평균 점수
        BigDecimal mannerScore,
        // 완료된 판매 건수
        int totalSales,
        // 최근 매너 리뷰 목록
        List<ReviewResponseDTO> recentReviews
) {
}
