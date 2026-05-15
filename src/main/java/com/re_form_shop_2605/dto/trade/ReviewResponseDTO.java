package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.dto.member.MemberBriefDTO;

import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 매너 평가 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record ReviewResponseDTO(
        // 리뷰 번호
        Long mannerId,
        // 거래 번호
        Long tradeId,
        // 작성자 정보
        MemberBriefDTO buyer,
        // 판매자 정보
        MemberBriefDTO seller,
        // 별점
        int score,
        // 후기 본문
        String content,
        // 생성일
        LocalDateTime createdAt
) {
}
