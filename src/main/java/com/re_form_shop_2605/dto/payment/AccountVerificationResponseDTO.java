package com.re_form_shop_2605.dto.payment;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-15
 * 설명: 계좌 실명 인증 응답 DTO
 *      - 금융결제원 계좌실명조회 API 응답 구조 기반
 * ─────────────────────────────────────────────────────
 */

public record AccountVerificationResponseDTO(
        boolean verified,         // 성공 여부 (rsp_code로 판단)
        String accountHolderName, // 계좌주 이름
        String message            // 결과 메시지 (rsp_message에서 받아옴)
) { }