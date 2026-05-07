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
//@Table(name = "wish",uniqueConstraints = {
//        @UniqueConstraint(name = "uk_wish_member_post", columnNames = {"member_id", "post_id"})
//})
//public class Wish extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "wish_id", nullable = false)
//    private Long wishId;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(
//            name = "member_id",
//            nullable = false,
//            foreignKey = @ForeignKey(name = "fk_wish_member")
//    )
//    private Member member;
//
////    @ManyToOne(fetch = FetchType.LAZY, optional = false)
////    @JoinColumn(
////            name = "post_id",
////            nullable = false,
////            foreignKey = @ForeignKey(name = "fk_wish_post")
////    )
////    private Post post
//
//}
