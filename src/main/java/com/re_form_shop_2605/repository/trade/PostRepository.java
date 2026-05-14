package com.re_form_shop_2605.repository.trade;

import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.trade.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 게시글 JPA 리포지토리 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByStatusNotIn(List<PostStatus> statuses);

    /**
     * 작성자: 손민정
     * 작성일: 2026-05-14
     * 설명: 미검사 판매 게시물 조회 (위험 탐지 재검사용)
     */
    /* 미검사 & 지정 Status가 아닌 게시물 조회 */
    List<Post> findAllByRiskLevelIsNullAndStatusNotIn(List<PostStatus> statuses);
}
