package com.re_form_shop_2605.domain.community;

import lombok.*;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-08
 * 설명: 커뮤니티 댓글, 대댓글 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyVO {
    private Long replyId;
    private Long postId;
    private Long memberId;
    private Long parentId; // 대댓글의 부모 댓글
    private String replyContent; // 댓글 내용
    private Boolean isDeleted;
    private int likeCount;
    private LocalDateTime createdAt;
}
