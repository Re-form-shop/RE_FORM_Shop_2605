package com.re_form_shop_2605.domain.member;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InterestKeywordVO {
    private Long keywordId; // id
    private Long memberId; // 회원 ID
    private String keyword; // 관심 키워드
}
