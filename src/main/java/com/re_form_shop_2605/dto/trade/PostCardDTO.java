package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.Sport;

import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 판매글 목록 카드 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record PostCardDTO(
        // 게시글 번호
        Long postId,
        // 제목
        String title,
        // 구단명
        String team,
        // 종목
        Sport sport,
        // 가격
        int price,
        // 유니폼 등급
        Grade grade,
        // 유니폼 사이즈
        String size,
        // 수령 방법
        DeliveryType deliveryType,
        // 판매 상태
        PostStatus status,
        // 조회수
        int viewCount,
        // 찜 수
        int wishCount,
        // 로그인 사용자 기준 찜 여부
        boolean isWished,
        // 썸네일 이미지 URL
        String thumbnailUrl,
        // 상대 시간 문자열
        String timeAgo,
        // 생성일
        LocalDateTime createdAt,
        // 판매자 요약 정보
        SellerBriefDTO seller
) {
}
