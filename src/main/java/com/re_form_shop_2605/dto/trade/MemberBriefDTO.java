package com.re_form_shop_2605.dto.trade;
/**
 * 작성자: 민기
 * 작성일: 2026-05-08
 * 설명:
 */
// 채팅/거래/리뷰에서 재사용하는 회원 요약 DTO
public record MemberBriefDTO(
        // 회원 번호
        Long memberId,
        // 닉네임
        String nickname,
        // 프로필 이미지 URL
        String profileImageUrl
) {
}
