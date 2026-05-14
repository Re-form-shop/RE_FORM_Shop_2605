package com.re_form_shop_2605.domain.trade;

import lombok.*;

import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 찜 정보 mybatis 전용 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WishVO {
    private Long wishId; // 찜 id
    private Long memberId; // 회원 id
    private Long postId; // 게시글 id
    private LocalDateTime createdAt; // 생성일
}
