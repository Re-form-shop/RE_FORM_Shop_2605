package com.re_form_shop_2605.domain.trade;

import lombok.*;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 게시글 이미지 mybatis 전용 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostImageVO {
    private Long imageId; // 이미지 id
    private Long postId; // 게시글 id
    private String imageUrl; // 이미지 url
    private int sortOrder; // 이미지 순서
}
