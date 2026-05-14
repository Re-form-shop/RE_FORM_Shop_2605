package com.re_form_shop_2605.dto.admin;

import com.re_form_shop_2605.entity.Enum.MemberStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관리자 회원 목록 아이템 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminMemberListDTO(
        // 회원 번호
        Long memberId,
        // 이메일
        String email,
        // 닉네임
        String nickname,
        // 회원 상태
        MemberStatus status,
        // 경고 횟수
        int warningCount,
        // 매너 평균 점수
        BigDecimal mannerScore,
        // 가입일
        LocalDateTime createdAt
) {
}
