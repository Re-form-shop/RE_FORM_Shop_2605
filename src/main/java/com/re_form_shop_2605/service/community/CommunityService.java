package com.re_form_shop_2605.service.community;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.community.*;
import com.re_form_shop_2605.entity.Enum.Sport;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: CommunityService의 interface
 * ─────────────────────────────────────────────────────
 */
public interface CommunityService {

    // 게시글 목록 조회 (종목 필터 + 페이지)
    PageResponse<CommunityPostListItemDTO> readPosts(Sport sport, int page, int size);

    // 게시글 상세 조회
    // todo viewerId: 비로그인이면 null → isLiked = false
    CommunityPostDetailDTO readPost(Long commId, Long viewerId);

    // 게시글 작성
    Long addPost(Long memberId, CommunityPostCreateRequestDTO requestDTO);

    // 게시글 수정
    void modifyPost(Long commId, Long memberId, CommunityPostUpdateRequestDTO requestDTO);

    // 게시글 삭제
    void removePost(Long commId, Long memberId);

    // 게시글 좋아요 토글
    // 누르면 +1 (INSERT), 다시 누르면 -1 (DELETE)
    int toggleLike(Long commId, Long memberId);

    // 댓글 목록 조회
    // todo viewerId: 비로그인이면 null → isLiked = false
    List<ReplyResponseDTO> readReplies(Long commId, Long viewerId);

    // 댓글 작성
    ReplyResponseDTO addReply(Long commId, Long memberId, ReplyCreateRequestDTO requestDTO);

    // 댓글 삭제 (soft delete)
    void removeReply(Long replyId, Long memberId);

    // 댓글 수정
    ReplyResponseDTO modifyReply(Long replyId, Long memberId, ReplyCreateRequestDTO requestDTO);

    // 댓글 좋아요 토글
    int toggleReplyLike(Long replyId, Long memberId);
}
