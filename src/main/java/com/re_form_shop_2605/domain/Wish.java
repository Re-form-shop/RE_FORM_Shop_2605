package com.re_form_shop_2605.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Wish {
    private Long wishId; // 찜 id
    private Long memberId; // 회원 id
    private Long postId; // 게시글 id
    private LocalDateTime createdAt; // 생성일
}
