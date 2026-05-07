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
//@Table(name = "post_image")
//public class PostImage {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "image_id", nullable = false)
//    private Long imageId; // 이미지 id
//
////    @ManyToOne(fetch = FetchType.LAZY, optional = false)
////    @JoinColumn(
////            name = "post_id",
////            nullable = false,
////            foreignKey = @ForeignKey("fk_post_image_post")
////    )
////    private Post post; // 게시글 id
//
//    @Column(name = "image_url", nullable = false, length = 500)
//    private String imageUrl; // 이미지 url
//
//    @Column(name = "sort_order")
//    private int sortOrder; // 이미지 순서
//}
