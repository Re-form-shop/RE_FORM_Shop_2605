package com.re_form_shop_2605.dto.member;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 닉네임 중복 확인 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record NicknameResponseDTO(
        // 닉네임 사용 가능 여부
        boolean available
) {
}
