package com.re_form_shop_2605.dto.login;

import com.re_form_shop_2605.entity.Enum.Role;

import java.math.BigDecimal;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 로그인/회원가입 응답에 공통으로 포함되는 인증 사용자 정보 DTO
 * ─────────────────────────────────────────────────────
 */
public record AuthUserDTO(
        // 회원 번호
        Long id,
        // 회원 이메일
        String email,
        // 회원 닉네임
        String nickname,
        // 프로필 이미지 URL
        String profileImageUrl,
        // 회원 권한
        Role role,
        // 매너 평균 점수
        BigDecimal mannerScore
) {
}
