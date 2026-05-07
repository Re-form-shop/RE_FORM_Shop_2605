//package com.re_form_shop_2605.entity.payment;
//
//import com.re_form_shop_2605.entity.BaseEntity;
//import com.re_form_shop_2605.entity.Enum.PayMethod;
//import com.re_form_shop_2605.entity.Enum.PaymentStatus;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "payment")
//@Getter
//@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//public class Payment extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "payment_id")
//    private Long paymentId; // 결제 번호
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "trade_id", nullable = false)
//    private Trade trade; // 거래 ID (FK)
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "pay_method", nullable = false)
//    private PayMethod payMethod; // 결제 방식
//
//    @Column(name = "approval_no", length = 50)
//    private String approvalNo; //카드 승인 번호
//
//    @Column(name = "toss_order_id", nullable = false, unique = true, length = 100)
//    private String tossOrderId; // Toss 주문 ID
//
//    @Column(name = "toss_payment_key", length = 200)
//    private String tossPaymentKey; // Toss 결제 키
//
//    @Column(name = "amount", nullable = false)
//    private Integer amount; // 결제 금액
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private PaymentStatus status; // 결제 上태
//
//    @Column(name = "paid_at")
//    private LocalDateTime paidAt; // 결제 완료 일시
//
//    /* 결제 상태 처리 */
//    // 1) 결제 승인 완료 시 (PAID)
//    public void paid(String tossPaymentKey, String approvalNo) {
//        this.tossPaymentKey = tossPaymentKey;
//        this.approvalNo = approvalNo;
//        this.status = PaymentStatus.PAID;
//        this.paidAt = LocalDateTime.now();
//    }
//
//    // 2) 결제 실패 시 (FAILED)
//    public void fail() {
//        this.status = PaymentStatus.FAILED;
//    }
//
//    // 3) 결제 취소 시 (CANCELED)
//    public void cancel() {
//        this.status = PaymentStatus.CANCELED;
//    }
//
//    // 4) 결제 환불 시 (REFUNDED)
//    public void refund() {
//        this.status = PaymentStatus.REFUNDED;
//    }
//}