package com.re_form_shop_2605.domain.payment;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
public class PointWalletVO {
    private Long walletId;
    private Long memberId; // 회원 아이디
    private Integer balance; // 회원별 보유 포인트
    private Integer withdrawable; // 출금 가능 포인트
    private Integer pending; // 출금 대기 포인트
}