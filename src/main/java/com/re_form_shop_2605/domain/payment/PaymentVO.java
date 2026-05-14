package com.re_form_shop_2605.domain.payment;

import com.re_form_shop_2605.entity.Enum.PayMethod;
import com.re_form_shop_2605.entity.Enum.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class PaymentVO {
    private Long paymentId;        // 결제 번호
    private Long tradeId;          // 거래 ID (FK)
    private PayMethod payMethod;   // 결제 방식
    private String approvalNo;     // 카드 승인 번호
    private String tossOrderId;    // Toss 주문 ID
    private String tossPaymentKey; // Toss 결제 키
    private Integer amount;        // 결제 금액
    private PaymentStatus status;  // 결제 상태
    private LocalDateTime paidAt;  // 결제 완료 일시
    private LocalDateTime createdAt;
}