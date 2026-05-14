package com.re_form_shop_2605.domain.member;

import lombok.*;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관심 키워드 mybatis 전용 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InterestKeywordVO {
    private Long keywordId; // id
    private Long memberId; // 회원 ID
    private String keyword; // 관심 키워드
}
