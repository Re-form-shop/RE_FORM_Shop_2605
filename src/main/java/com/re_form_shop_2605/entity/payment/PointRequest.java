//package com.re_form_shop_2605.entity.payment;
//
//import com.re_form_shop_2605.entity.BaseEntity;
//import com.re_form_shop_2605.entity.Enum.PointRequestStatus;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "point_request")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class PointRequest extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "withdraw_id")
//    private Long withdrawId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", nullable = false)
//    private Member member; // 회원 ID
//
//    @Column(name = "request_amount", nullable = false)
//    private Integer requestAmount; // 출금 요청 금액
//
//    @Column(name = "bank_name", length = 50)
//    private String bankName; // 은행명
//
//    @Column(name = "account_number", length = 30)
//    private String accountNumber; // 계좌번호
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false)
//    private PointRequestStatus status; // 출금 처리 상태
//
//    @Column(name = "reject_reason", length = 300)
//    private String rejectReason; // 반려 사유
//
//    /* 포인트 현금화 요청 상태 변경 */
//    // 1) 최초 생성 시
//    @Builder
//    public PointRequest(Member member, Integer requestAmount, String bankName, String accountNumber) {
//        this.member = member;
//        this.requestAmount = requestAmount;
//        this.bankName = bankName;
//        this.accountNumber = accountNumber;
//        this.status = PointRequestStatus.PENDING;
//    }
//
//    // 2) 승인
//    public void approve() {
//        this.status = PointRequestStatus.APPROVED;
//    }
//
//    // 3) 반려
//    public void reject(String reason) {
//        this.status = PointRequestStatus.REJECTED;
//        this.rejectReason = reason;
//    }
//}