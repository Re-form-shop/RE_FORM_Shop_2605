//package com.re_form_shop_2605.entity.payment;
//
//import com.re_form_shop_2605.entity.BaseEntity;
//import com.re_form_shop_2605.entity.Enum.PointHistoryType;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//
//@Entity
//@Table(name = "point_history")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class PointHistory extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "point_id")
//    private Long pointId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "wallet_id", nullable = false)
//    private PointWallet pointWallet;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "trade_id", unique = true)
//    private Trade trade; // 중복 지급 방지 거래 ID
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "type", nullable = false)
//    private PointHistoryType type; // 지급 / 반려
//
//    @Column(name = "change_amount", nullable = false)
//    private Integer changeAmount; // 변동량
//
//    @Column(name = "balance", nullable = false)
//    private Integer balance; // 회원별 보유 포인트
//}