package com.re_form_shop_2605.dto.payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-11
 * 설명: 포인트 출금 신청 요청 DTO (프론트 → 백)
 * ─────────────────────────────────────────────────────
 */

public record WithdrawRequestDTO(
        @NotNull @Min(1000)
        int requestAmount,    // 출금 신청 금액

        @NotBlank @Size(max = 50)
        String bankName,      // 은행명

        @NotBlank @Size(max = 30)
        String accountNumber, // 계좌번호

        // 05.15 추가) 계좌 실명 인증 추가 필드 2개
        @NotBlank
        String bankCode,      // 은행 코드

        @NotBlank
        String holderInfo     // 계좌주 생년월일 6자리
) { }