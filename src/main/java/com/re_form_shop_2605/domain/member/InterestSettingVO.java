package com.re_form_shop_2605.domain.member;

import com.re_form_shop_2605.entity.Enum.Sport;
import lombok.*;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관심 종목 mybatis 전용 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InterestSettingVO {
    private Long memberId; // 회원 id
    private Sport sport; // 관심 종목 (야구/농구/배구/이스포츠)
    private String team; // 관심 구단
}
