package com.re_form_shop_2605.domain;

import lombok.*;
import org.w3c.dom.Text;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MannerReview {
    private Long mannerId; // id
    private Long tradeId; // 거래 id
    private Long buyerId; // 판매자 id
    private Long sellerId; // 구매자 id
    private double score; // 매너점수
    private Text content; // 후기 본문
    private LocalDateTime createdAt; // 생성일
}
