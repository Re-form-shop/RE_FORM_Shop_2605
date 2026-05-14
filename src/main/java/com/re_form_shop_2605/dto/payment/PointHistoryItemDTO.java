package com.re_form_shop_2605.dto.payment;

import com.re_form_shop_2605.entity.Enum.PointHistoryType;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-11
 * 설명: 포인트 이력 단건 응답 DTO
 * ─────────────────────────────────────────────────────
 */

public record PointHistoryItemDTO(
        Long pointId,
        PointHistoryType type,    // EARN | WITHDRAW
        int changeAmount,         // 변동량 (적립 양수 / 차감 음수)
        int balance,              // 변동 후 잔액
        Long tradeId,             // 연결 거래 ID (null 가능)
        LocalDateTime createdAt
) {
    /* UC-PAY-005 : 포인트 적립/차감 이력 최신순 조회 */
}
