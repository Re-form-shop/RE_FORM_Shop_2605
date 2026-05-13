package com.re_form_shop_2605.repository.community;

import com.re_form_shop_2605.entity.community.CommunityLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: 커뮤니티 좋아요 Repository
 * ─────────────────────────────────────────────────────
 */
public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Long> {

    // isLiked 여부 확인
    boolean existsByMember_MemberIdAndCommunityPost_CommId(Long memberId, Long commId);

    // 좋아요 취소 시 삭제할 행 조회
    Optional<CommunityLike> findByMember_MemberIdAndCommunityPost_CommId(Long memberId, Long commId);
}
