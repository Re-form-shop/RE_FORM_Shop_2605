package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.RiskLevel;
import com.re_form_shop_2605.entity.Enum.Sport;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 판매글 상세 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record PostDetailDTO(
        // 게시글 번호
        Long postId,
        // 제목
        String title,
        // 본문
        String content,
        // 종목
        Sport sport,
        // 구단명
        String team,
        // 유니폼명
        String uniformName,
        // 유니폼 등급
        Grade grade,
        // 유니폼 사이즈
        String size,
        // 마킹 여부
        Boolean marking,
        // 가격
        int price,
        // 수령 방법
        DeliveryType deliveryType,
        // 판매 상태
        PostStatus status,
        // 위험 탐지 등급
        RiskLevel riskLevel,
        // 조회수
        int viewCount,
        // 찜 수
        int wishCount,
        // 로그인 사용자 기준 찜 여부
        boolean isWished,
        // 이미지 URL 목록
        List<String> imageUrls,
        // 생성일
        LocalDateTime createdAt,
        // 수정일
        LocalDateTime updatedAt,
        // 판매자 요약 정보
        SellerBriefDTO seller,
        // 연결된 거래 번호
        Long tradeId
) {
}
