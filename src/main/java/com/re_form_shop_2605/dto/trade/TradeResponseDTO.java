package com.re_form_shop_2605.dto.trade;

import com.re_form_shop_2605.dto.member.MemberBriefDTO;
import com.re_form_shop_2605.dto.chat.PostBriefDTO;
import com.re_form_shop_2605.entity.Enum.TradeDeliveryType;
import com.re_form_shop_2605.entity.Enum.TradeStatus;

import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 거래 상세 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record TradeResponseDTO(
        // 거래 번호
        Long tradeId,
        // 판매글 요약 정보
        PostBriefDTO post,
        // 구매자 정보
        MemberBriefDTO buyer,
        // 판매자 정보
        MemberBriefDTO seller,
        // 거래 상태
        TradeStatus status,
        // 전달 방식
        TradeDeliveryType deliveryType,
        // 배송 주소
        String deliveryAddress,
        // 택배사 코드
        String courierCode,
        // 택배사명
        String courierName,
        // 송장번호
        String trackingNumber,
        // 실제 거래 금액
        int tradePrice,
        // 배송 시작 일시
        LocalDateTime shippingStartedAt,
        // 정산 완료 일시
        LocalDateTime completedAt,
        // 구매 확정 일시
        LocalDateTime confirmedAt,
        // 거래 생성일
        LocalDateTime createdAt,
        // 리뷰 작성 여부
        boolean hasReview
) {
}
