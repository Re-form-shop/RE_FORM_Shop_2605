package com.re_form_shop_2605.repository.trade;

import com.re_form_shop_2605.entity.trade.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 게시글 이미지 JPA 리포지토리 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface postImageRepository  extends JpaRepository<PostImage,Long> {
    List<PostImage> findAllByPost_PostIdOrderBySortOrderAsc(Long postId);

    void deleteByPost_PostId(Long postId);
}
