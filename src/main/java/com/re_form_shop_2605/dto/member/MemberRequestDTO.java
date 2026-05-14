package com.re_form_shop_2605.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 이메일 회원가입 요청 DTO
 * ─────────────────────────────────────────────────────
 */
public record MemberRequestDTO(
        // 회원 이메일
        @NotBlank
        @Email
        @Size(max = 100)
        String email,

        // 회원 닉네임
        @NotBlank
        @Size(min = 2, max = 20)
        String nickname,

        // 회원 비밀번호
        @NotBlank
        @Size(min = 8)
        String password,

        // 마케팅 수신 동의 여부
        boolean marketingAgreed
) {
}
