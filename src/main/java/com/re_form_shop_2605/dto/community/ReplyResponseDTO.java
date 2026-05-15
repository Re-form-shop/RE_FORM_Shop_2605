package com.re_form_shop_2605.dto.community;

import com.re_form_shop_2605.dto.member.MemberBriefDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: 커뮤니티 게시글 댓글 조회용 DTO
 * ─────────────────────────────────────────────────────
 */
public record ReplyResponseDTO(
        Long replyId,
        MemberBriefDTO author,
        String replyContent,
        int likeCount,
        boolean isLiked,
        boolean isDeleted,
        LocalDateTime createdAt,
        List<ReplyResponseDTO> children // 대댓글 목록 (최대 1단계)
) {
}
