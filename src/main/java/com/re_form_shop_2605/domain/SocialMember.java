package com.re_form_shop_2605.domain;

import com.re_form_shop_2605.entity.Enum.Provider;
import com.re_form_shop_2605.entity.Member;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SocialMember {
    private int socialId; // 소셜 id
    private Long member_id; // 회원 id
    private Provider provider; // 소셜 로그인 경로 (카카오/구글)
    private String providerId; // 소셜 제공자 id
}
