package com.re_form_shop_2605.repository.trade;

import com.re_form_shop_2605.entity.trade.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 찜 등록 JPA 리포지토리 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface WishRepository extends JpaRepository<Wish, Long> {
    boolean existsByMember_MemberIdAndPost_PostId(Long memberId, Long postId);

    Optional<Wish> findByMember_MemberIdAndPost_PostId(Long memberId, Long postId);

    long countByPost_PostId(Long postId);

    List<Wish> findAllByPost_PostId(Long postId);

    // 특정 회원이 찜한 목록을 최신순으로 조회 (내 찜 목록 탭용)
    List<Wish> findAllByMember_MemberIdOrderByCreatedAtDesc(Long memberId);
}
