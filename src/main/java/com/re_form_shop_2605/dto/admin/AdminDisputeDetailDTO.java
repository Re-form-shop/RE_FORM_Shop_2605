package com.re_form_shop_2605.dto.admin;

import com.re_form_shop_2605.entity.Enum.TradeDeliveryType;
import com.re_form_shop_2605.entity.Enum.TradeStatus;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-24
 * 설명: 관리자 분쟁 상세 조회 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminDisputeDetailDTO(
        // 거래 번호
        Long tradeId,
        // 상품 번호
        Long postId,
        // 상품명
        String postTitle,
        // 거래 금액
        int price,
        // 거래 상태
        TradeStatus status,
        // 배송 방식
        TradeDeliveryType deliveryType,
        // 배송지
        String deliveryAddress,
        // 택배사 코드
        String courierCode,
        // 택배사명
        String courierName,
        // 송장번호
        String trackingNumber,
        // 거래 생성 시각
        LocalDateTime createdAt,
        // 배송 시작 시각
        LocalDateTime shippingStartedAt,
        // 구매 확정 시각
        LocalDateTime confirmedAt,
        // 거래 완료 시각
        LocalDateTime completedAt,
        // 구매자 분쟁 제기 시각
        LocalDateTime disputedAt,
        // 관리자 처리 시각
        LocalDateTime processedAt,
        // 처리 관리자 닉네임
        String processedBy,
        // 관리자 처리 메모
        String adminMemo,
        // 마지막 처리 결과
        String resolutionType,
        // 분쟁 연장 종료 시각
        LocalDateTime extendedUntil,
        // 구매자 회원 번호
        Long buyerMemberId,
        // 구매자 닉네임
        String buyerNickname,
        // 구매자 이메일
        String buyerEmail,
        // 구매자 분쟁 사유
        String buyerClaim,
        // 판매자 회원 번호
        Long sellerMemberId,
        // 판매자 닉네임
        String sellerNickname,
        // 판매자 이메일
        String sellerEmail,
        // 판매자 소명
        String sellerClaim
) {
}
