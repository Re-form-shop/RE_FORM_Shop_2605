package com.re_form_shop_2605.domain.trade;

import lombok.*;

import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 게시글 mybatis 전용 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostVO {
    private Long postId; // 게시글 번호
    private Long sellerId; // 판매자 member id
    private String title; // 제목
    private String content; // 본문
    private String sport; // 종목
    private String team; // 구단명
    private String uniformName; // 유니폼명
    private String grade; // 유니폼 상태 등급
    private String size; // 유니폼 사이즈 (85 ~120)
    private Boolean marking; // 마킹 여부
    private int price; // 희망 가격
    private String deliveryType; // 수령 방법
    private String status; // 게시글 상태
    private Integer viewCount; // 조회수
    private Integer wishCount; // 찜 횟수
    private String riskLevel; // 위험도
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일
}
