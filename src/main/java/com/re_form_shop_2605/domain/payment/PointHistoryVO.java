package com.re_form_shop_2605.domain.payment;

import com.re_form_shop_2605.entity.Enum.PointHistoryType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class PointHistoryVO {
    private Long pointId;
    private Long walletId;
    private Long tradeId; // 중복 지급 방지 거래 ID
    private PointHistoryType type; // 지급 / 반려
    private Integer changeAmount; // 변동량
    private Integer balance; // 회원별 보유 포인트
    private LocalDateTime createdAt;
}