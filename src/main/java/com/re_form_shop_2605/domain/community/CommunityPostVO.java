package com.re_form_shop_2605.domain.community;

import com.re_form_shop_2605.entity.Enum.CommunityPostStatus;

import com.re_form_shop_2605.entity.Enum.Sport;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-08
 * 설명: 커뮤니티 게시글 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityPostVO {
    private Long commId;
    private Long memberId;
    private Sport Sport; // 종목
    private String teamCategory; // 구단명
    private String commTitle; // 제목
    private String commContent; // 본문
    private String commImageUrl; // 첨부 이미지 URL
    private int commViewCount; // 조회수
    private int likeCount; // 좋아요 수
    private int commentCount; // 댓글 수
    private CommunityPostStatus status; // 게시글 상태
    private LocalDateTime createdAt;
}
