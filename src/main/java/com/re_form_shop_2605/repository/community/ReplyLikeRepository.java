package com.re_form_shop_2605.repository.community;

import com.re_form_shop_2605.entity.community.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: 댓글 좋아요 Repository
 * ─────────────────────────────────────────────────────
 */
public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {

    // isLiked 여부 확인
    boolean existsByMember_MemberIdAndReply_ReplyId(long memberId, long replyId);

    // 좋아요 취소 시 삭제할 행 조회
    Optional<ReplyLike> findByMember_MemberIdAndReply_ReplyId(Long memberId, Long replyId);
}
