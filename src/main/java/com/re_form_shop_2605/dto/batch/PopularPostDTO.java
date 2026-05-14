package com.re_form_shop_2605.dto.batch;

import com.re_form_shop_2605.entity.Enum.Sport;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-13
 * 설명: 커뮤니티 인기글 응답 DTO
 * - Redis 저장 및 API 응답에 사용
 * - score = commViewCount * 1 + likeCount * 3 + commentCount * 2
 * ─────────────────────────────────────────────────────
 */

public record PopularPostDTO(
        Long commId,               // 게시물 id
        String commTitle,          // 제목
        Sport sportCategory,       // 종목
        String teamCategory,       // 구단
        int commViewCount,         // 조회수
        int likeCount,             // 좋아요 수
        int commentCount,          // 댓글 수
        double score,              // 인기도 점수 (조회수*1 + 좋아요*3 + 댓글*2)
        String writerNickName,     // 작성자 닉네임
        String writerProfileImage, // 작성자 프로필 이미지
        LocalDateTime createdAt
) { }