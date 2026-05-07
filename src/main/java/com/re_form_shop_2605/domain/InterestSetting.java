package com.re_form_shop_2605.domain;

import com.re_form_shop_2605.entity.Enum.SportType;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InterestSetting {
    private Long memberId; // 회원 id
    private SportType sport; // 관심 종목 (야구/농구/배구/이스포츠)
    private String team; // 관심 구단
}
