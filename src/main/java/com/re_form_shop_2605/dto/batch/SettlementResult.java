package com.re_form_shop_2605.dto.batch;

import com.re_form_shop_2605.entity.payment.PointWallet;
import com.re_form_shop_2605.entity.trade.Trade;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-09
 * 설명: 정산 배치 Processor → Writer 전달용 DTO
 * ─────────────────────────────────────────────────────
 */

public record SettlementResult(
        PointWallet wallet, // 판매자 포인트 지갑
        Trade trade,        // 정산 대상 거래
        int point           // 수수료 차감 후 지급 포인트
) {}