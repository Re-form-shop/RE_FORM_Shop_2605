package com.re_form_shop_2605.domain.trade;

import com.re_form_shop_2605.entity.Enum.TradeDeliveryType;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import lombok.*;

import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 거래 정보 mybatis 전용 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TradeVO {
    private Long tradeId; // 거래 id
    private Long postId; // 게시글 id
    private Long buyerId; // 판매자 id
    private Long sellerId; // 구매자 id
    private TradeStatus status; // 거래 상태
    private TradeDeliveryType  deliveryType; // 전달 방법 (수령 / 배송)
    private String deliveryAddress; // 배송 주소(배송 시 해당사항)
    private int tradePrice; // 실제 거래 금액
    private LocalDateTime completedAt; // 정산 완료 일시
    private LocalDateTime confirmedAt; // 구매 확정 일시
    private LocalDateTime createdAt; // 생성일
}
