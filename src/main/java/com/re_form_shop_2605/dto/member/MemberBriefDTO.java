package com.re_form_shop_2605.dto.member;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-09
 * 설명: 회원 정보 DTO
 * ─────────────────────────────────────────────────────
 */

public record MemberBriefDTO(
        Long memberId,
        String nickname,
        String profileImageUrl
) {}