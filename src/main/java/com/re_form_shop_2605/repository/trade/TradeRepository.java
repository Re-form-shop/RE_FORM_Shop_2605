package com.re_form_shop_2605.repository.trade;

import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.entity.trade.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    /* 거래 상태 변경 */
    // 1. TradeStatus 따른 거래 조회
    // 용도 1) 미정산 거래 조회 (status = CONFIRMED && trade_id.point_history IS NULL)
    @Query("SELECT t FROM Trade t WHERE t.status = :status " +
            "AND NOT EXISTS (SELECT p FROM PointHistory p WHERE p.trade = t)")
    List<Trade> findConfirmedUnsettledTrades(@Param("status")TradeStatus status);
}
