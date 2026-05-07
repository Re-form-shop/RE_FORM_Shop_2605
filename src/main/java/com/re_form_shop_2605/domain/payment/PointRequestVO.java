package com.re_form_shop_2605.domain.payment;

import com.re_form_shop_2605.entity.Enum.PointRequestStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class PointRequestVO {
    private Long withdrawId;
    private Long memberId; // 회원 ID
    private Integer requestAmount; // 출금 요청 금액
    private String bankName; // 은행명
    private String accountNumber; // 계좌번호
    private PointRequestStatus status; // 출금 처리 상태
    private String rejectReason; // 반려 사유
    private LocalDateTime createdAt;
}