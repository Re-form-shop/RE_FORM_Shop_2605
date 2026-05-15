package com.re_form_shop_2605.dto.community;

import com.re_form_shop_2605.dto.member.MemberBriefDTO;
import com.re_form_shop_2605.entity.Enum.CommunityPostStatus;
import com.re_form_shop_2605.entity.Enum.Sport;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-10
 * 설명: 커뮤니티 게시글 상세 조회 DTO
 * ─────────────────────────────────────────────────────
 */
// GET /api/community/{commId}
public record CommunityPostDetailDTO(
        Long commId,
        Sport sport,
        String teamCategory,
        String commTitle,
        String commContent,
        String commImageUrl,
        int commViewCount,
        int likeCount,
        int commentCount,
        boolean isLiked, // 로그인 사용자 기준
        CommunityPostStatus status,
        MemberBriefDTO author,
        LocalDateTime createdAt
) {}
