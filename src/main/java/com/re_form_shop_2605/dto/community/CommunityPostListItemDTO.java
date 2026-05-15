package com.re_form_shop_2605.dto.community;

import com.re_form_shop_2605.dto.member.MemberBriefDTO;
import com.re_form_shop_2605.entity.Enum.CommunityPostStatus;
import com.re_form_shop_2605.entity.Enum.Sport;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-11
 * 설명: 커뮤니티 게시글 목록 조회용 DTO
 * ─────────────────────────────────────────────────────
 */
// GET /api/community → PageResponse<CommunityPostListItemDTO>
public record CommunityPostListItemDTO(
        Long commId,
        Sport sport,
        String teamCategory,
        String commTitle,
        int commViewCount,
        int likeCount,
        int commentCount,
        CommunityPostStatus status,
        MemberBriefDTO author,
        LocalDateTime createdAt
) {}
