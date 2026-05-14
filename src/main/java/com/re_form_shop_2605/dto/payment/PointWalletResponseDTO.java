package com.re_form_shop_2605.dto.payment;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-11
 * 설명: 포인트 지갑 조회 응답 DTO
 * ─────────────────────────────────────────────────────
 */

public record PointWalletResponseDTO(
        int balance,         // 총 보유 포인트
        int withdrawable,    // 출금 가능 포인트
        int pending          // 정산 대기 포인트 (구매 확정 전)
) {
    /* UC-PAY-005 : 보유 포인트, 출금 가능 포인트, 출금 대기 포인트 표시 */
}