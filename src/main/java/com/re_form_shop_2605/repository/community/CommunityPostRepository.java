package com.re_form_shop_2605.repository.community;

import com.re_form_shop_2605.entity.Enum.CommunityPostStatus;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.entity.community.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: 커뮤니티 Repository
 * ─────────────────────────────────────────────────────
 */
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {

    // 전체 목록 조회 (HIDDEN, DELETED 제외)
    List<CommunityPost> findAllByStatusNotIn(List<CommunityPostStatus> status);

    // 종목 필터 목록 조회 (HIDDEN, DELETED 제외)
    List<CommunityPost> findAllBySportCategoryAndStatusNotIn(
            Sport sportCategory,
            List<CommunityPostStatus> statuses
    );
}
