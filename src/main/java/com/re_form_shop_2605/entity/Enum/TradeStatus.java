package com.re_form_shop_2605.entity.Enum;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-07
 * 설명: 거래 진행 상태를 나타내는 enum
 * ─────────────────────────────────────────────────────
 */
public enum TradeStatus {
    REQUESTED, ACCEPTED, PAID, IN_PROGRESS, RECEIVED,
            CONFIRMED, COMPLETED, CANCELED, DISPUTED
}
