package com.re_form_shop_2605.domain;

import com.re_form_shop_2605.entity.Member;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InterestKeyword {
    private Long keywordId; // id
    private Long memberId; // 회원 ID
    private String keyword; // 관심 키워드
}
