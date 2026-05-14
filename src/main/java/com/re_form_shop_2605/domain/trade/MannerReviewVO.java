package com.re_form_shop_2605.domain.trade;

import lombok.*;

import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 매너 정보 mybatis 전용 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MannerReviewVO {
    private Long mannerId; // id
    private Long tradeId; // 거래 id
    private Long buyerId; // 판매자 id
    private Long sellerId; // 구매자 id
    private double score; // 매너점수
    private String content; // 후기 본문
    private LocalDateTime createdAt; // 생성일
}
