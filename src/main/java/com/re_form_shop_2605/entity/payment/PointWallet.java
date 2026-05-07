//package com.re_form_shop_2605.entity.payment;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "point_wallet")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class PointWallet {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "wallet_id")
//    private Long walletId;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id", nullable = false, unique = true)
//    private Member member; // 회원 아이디
//
//    @Column(name = "balance", nullable = false)
//    private Integer balance; // 회원별 보유 포인트
//
//    @Column(name = "withdrawable", nullable = false)
//    private Integer withdrawable; // 출금 가능 포인트
//
//    @Column(name = "pending", nullable = false)
//    private Integer pending; // 출금 대기 포인트
//
//    /*
//    포인트 보유 현황 프로세스
//    (1) 거래 완료 : +pending
//    (2) 구매 확정 : +balance, +withdrawable, -pending
//    (3) 출금 : -balance, -withdrawable
//     */
//    // 1) 최초 생성 시 모두 0으로 초기화
//    @Builder
//    public PointWallet(Member member) {
//        this.member = member;
//        this.balance = 0;
//        this.withdrawable = 0;
//        this.pending = 0;
//    }
//
//    // 2) 거래 완료 시
//    public void earnPoint(int amount) {
//        this.pending += amount;
//    }
//
//    // 3) 구매 확정 시
//    public void confirm(int amount) {
//        this.pending -= amount;
//        this.balance += amount;
//        this.withdrawable += amount;
//    }
//
//    // 4) 출금 시
//    public void withdraw(int amount) {
//        if (this.withdrawable < pending) {
//            throw new IllegalStateException("출금 가능 포인트가 부족합니다.");
//        }
//        this.withdrawable -= amount;
//        this.balance -= amount;
//    }
//}