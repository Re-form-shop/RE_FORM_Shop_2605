package com.re_form_shop_2605.dto.trade;

import java.math.BigDecimal;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 판매자 요약 정보 DTO
 * ─────────────────────────────────────────────────────
 */
public record SellerBriefDTO(
        // 회원 번호
        Long memberId,
        // 닉네임
        String nickname,
        // 프로필 이미지 URL
        String profileImageUrl,
        // 매너 평균 점수
        BigDecimal mannerScore
) {
}
