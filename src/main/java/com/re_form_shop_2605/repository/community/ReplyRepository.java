package com.re_form_shop_2605.repository.community;

import com.re_form_shop_2605.entity.community.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: 댓글 Repository
 * ─────────────────────────────────────────────────────
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // 특정 게시글의 최상위 댓글만 조회
    List<Reply> findAllByCommunityPost_CommIdAndReplyIsNullOrderByCreatedAtDesc(long commId);
}
