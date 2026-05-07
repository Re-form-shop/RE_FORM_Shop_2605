//package com.re_form_shop_2605.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Getter
//@ToString
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "manner_review",uniqueConstraints = {
//        @UniqueConstraint(name = "uk_manner_review_trade_buyer", columnNames = {"trade_id", "buyer_id"})
//})
//public class MannerReview extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "manner_id", nullable = false)
//    private Long mannerId;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(
//            name = "trade_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_manner_review_trade")
//    )
//    private Trade trade;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(
//            name = "buyer_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_manner_review_buyer_member")
//    )
//    private Member buyer;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(
//            name = "seller_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_manner_review_seller_member")
//    )
//    private Member seller;
//
//    @Lob
//    @Column(name = "content")
//    private String content;
//
//}
