package com.re_form_shop_2605.domain.trade;

import com.re_form_shop_2605.dto.trade.SellerBriefDTO;
import com.re_form_shop_2605.entity.Enum.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostCardVO {
    /* 검색용 */
    Long postId;                  // 게시글 번호
    String title;                 // 제목
    String team;                  // 구단명
    Sport sport;                  // 종목
    int price;                    // 가격
    Grade condition;                  // 유니폼 등급
    String size;                  // 유니폼 사이즈
    DeliveryType tradeType;    // 수령 방법
    PostStatus status;            // 판매 상태
    int viewCount;                // 조회수
    int likeCount;                // 찜 수
    boolean isLiked;             // 로그인 사용자 기준 찜 여부
    String thumbnailUrl;          // 썸네일 이미지 URL
    String timeAgo;               // 상대 시간 문자열
    LocalDateTime createdAt;      // 생성일
    Long sellerMemberId;          // 판매자 요약 정보
    String sellerNickname;        // 판매자 닉네임
    String sellerProfileImageUrl; // 판매자 프로필 사진
    BigDecimal sellerMannerScore; // 판매자 매너 온도
}