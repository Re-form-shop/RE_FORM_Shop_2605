package com.re_form_shop_2605.dto.admin;

import com.re_form_shop_2605.entity.Enum.TradeStatus;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-24
 * 설명: 관리자 화면 전반에서 공통으로 사용하는 거래 요약 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminTradeSummaryDTO(
        // 거래 번호
        Long tradeId,
        // 판매글 번호
        Long postId,
        // 판매글 제목
        String postTitle,
        // 조회 대상 회원 기준 거래 역할
        String role,
        // 거래 금액
        int price,
        // 거래 상태
        TradeStatus status,
        // 거래 생성 시각
        LocalDateTime createdAt,
        // 구매 확정 시각
        LocalDateTime confirmedAt,
        // 거래 완료 시각
        LocalDateTime completedAt,
        // 구매자 회원 번호
        Long buyerMemberId,
        // 구매자 닉네임
        String buyerNickname,
        // 판매자 회원 번호
        Long sellerMemberId,
        // 판매자 닉네임
        String sellerNickname
) {
}
