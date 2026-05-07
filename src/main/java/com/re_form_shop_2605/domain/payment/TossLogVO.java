package com.re_form_shop_2605.domain.payment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class TossLogVO {
    private Long logId; // 로그 번호
    private Long paymentId; // 결제 번호(FK)
    private String tossPaymentKey; // Toss 결제 키
    private String rawResponse; // 토스 API 원문
    private LocalDateTime createdAt;
}