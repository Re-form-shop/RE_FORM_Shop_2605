package com.re_form_shop_2605.dto.community;

import com.re_form_shop_2605.dto.chat.MemberBriefDTO;
import com.re_form_shop_2605.entity.Enum.CommunityPostStatus;
import com.re_form_shop_2605.entity.Enum.Sport;

import java.time.LocalDateTime;

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
