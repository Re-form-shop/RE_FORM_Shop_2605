package com.re_form_shop_2605.entity.payment;

import com.re_form_shop_2605.entity.BaseEntity;
import com.re_form_shop_2605.entity.Enum.PointHistoryType;
import com.re_form_shop_2605.entity.trade.Trade;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-07
 * 설명: 포인트 이력 Entity
 *      - 포인트 적립/차감 이력 저장
 *      - type: EARN(적립) | WITHDRAW(출금)
 *      - trade: 중복 지급 방지용 거래 ID (unique)
 * ─────────────────────────────────────────────────────
 */

@Entity
@Table(name = "point_history")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long pointId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private PointWallet pointWallet;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id", unique = true)
    private Trade trade; // 중복 지급 방지 거래 ID

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PointHistoryType type; // 지급 / 반려

    @Column(name = "change_amount", nullable = false)
    private Integer changeAmount; // 변동량

    @Column(name = "balance", nullable = false)
    private Integer balance; // 회원별 보유 포인트
}