package com.re_form_shop_2605.dto.payment;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-15
 * 설명: 계좌 실명 인증 요청 DTO
 *      - 금융결제원 계좌실명조회 API 요청 구조 기반
 * ─────────────────────────────────────────────────────
 */

public record AccountVerificationRequestDTO(
        String bankCode,   // 은행 코드
        String accountNum, // 계좌번호
        String holderInfo  // 계좌주 생년월일 6자리
) {}
