package com.re_form_shop_2605.repository.community;

import com.re_form_shop_2605.entity.Enum.CommunityPostStatus;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.entity.community.CommunityPost;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: 커뮤니티 Repository (진혜림)
 * ─────────────────────────────────────────────────────
 */
public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    /**
     * ─────────────────────────────────────────────────────
     * 작성자: 손민정
     * 작성일: 2026-05-13
     * 설명: 배치용 24시간 이내 게시글 조회
     * ─────────────────────────────────────────────────────
     */
    /* 최근 24시간 이내 게시글 조회 (배치용) */
    @Query("SELECT c FROM CommunityPost c JOIN FETCH c.member " +
            "WHERE c.createdAt >= :since AND c.status = :status")
    List<CommunityPost> findRecentPosts(@Param("since") LocalDateTime since,
                                        @Param("status") CommunityPostStatus status
    );

    // 전체 목록 조회 (HIDDEN, DELETED 제외)
    List<CommunityPost> findAllByStatusNotIn(List<CommunityPostStatus> status);

    // 종목 필터 목록 조회 (HIDDEN, DELETED 제외)
    List<CommunityPost> findAllBySportCategoryAndStatusNotIn(
            Sport sportCategory,
            List<CommunityPostStatus> statuses
    );
}
