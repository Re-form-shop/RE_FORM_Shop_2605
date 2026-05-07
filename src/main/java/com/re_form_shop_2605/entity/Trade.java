//package com.re_form_shop_2605.entity;
//
//import com.re_form_shop_2605.entity.Enum.TradeDeliveryType;
//import com.re_form_shop_2605.entity.Enum.TradeStatus;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Getter
//@ToString
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "trade")
//public class Trade extends BaseEntity {
//
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    @Column(name = "trade_id", nullable = false)
////    private Long tradeId;
////
////    @OneToOne(fetch = FetchType.LAZY, optional = false)
////    @JoinColumn(
////            name = "post_id",
////            nullable = false,
////            unique = true,
////            foreignKey = @ForeignKey(name = "fk_trade_post")
////    )
////    private Post post
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(
//            name = "buyer_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_trade_buyer_member")
//    )
//    private Member buyer;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(
//            name = "seller_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_trade_seller_member")
//    )
//    private Member seller;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private TradeStatus status;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "delivery_type")
//    private TradeDeliveryType deliveryType;
//
//    @Column(name = "delivery_address", length = 300)
//    private String deliveryAddress;
//
//    @Column(name = "trade_price", nullable = false)
//    private Integer tradePrice;
//
//    @Column(name = "completed_at")
//    private LocalDateTime completedAt;
//
//    @Column(name = "confirmed_at")
//    private LocalDateTime confirmedAt;
//
//    @OneToOne(mappedBy = "trade", fetch = FetchType.LAZY)
//    private MannerReview mannerReview;
//
//}
