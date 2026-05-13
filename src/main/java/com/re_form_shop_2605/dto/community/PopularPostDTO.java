//package com.re_form_shop_2605.dto.community;
//
//import com.re_form_shop_2605.entity.Enum.Sport;
//
//import java.time.LocalDateTime;
//
//public record PopularPostDTO() {
//    /* 인기글 조회용 */
//    Long commId, // 게시물 id
//    String commTitle, // 제목
//    Sport sportCategory, // 종목
//    String teamCategory, // 구단
//    int commViewCount, // 조회수
//    int commentCount, // 좋아요 수
//    double score, // 인기 척도 판단용 게시물 점수
//    String writerNickName, // 작성자 닉네임
//    String writerProfileImage, // 작성자 프로필 이미지
//    LocalDateTime createdAt
//}