package com.re_form_shop_2605.domain.member;

import com.re_form_shop_2605.entity.Enum.Provider;
import lombok.*;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 소셜 회원 mybatis 전용 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SocialMemberVO {
    private Long socialId; // 소셜 id
    private Long member_id; // 회원 id
    private Provider provider; // 소셜 로그인 경로 (카카오/구글)
    private String providerId; // 소셜 제공자 id
}
