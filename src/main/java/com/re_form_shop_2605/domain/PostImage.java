package com.re_form_shop_2605.domain;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostImage {
    private Long imageId; // 이미지 id
    private Long postId; // 게시글 id
    private String imageUrl; // 이미지 url
    private int sortOrder; // 이미지 순서
}
