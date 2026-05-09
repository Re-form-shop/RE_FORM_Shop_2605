package com.re_form_shop_2605.dto.batch;

import com.re_form_shop_2605.entity.payment.PointWallet;
import com.re_form_shop_2605.entity.trade.Trade;

public record SettlementResult(
        PointWallet wallet,
        Trade trade,
        int point
) {}