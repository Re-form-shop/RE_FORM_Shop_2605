package com.re_form_shop_2605.repository.AI;

import com.re_form_shop_2605.entity.AI.RiskAnalysisResult;
import com.re_form_shop_2605.entity.AI.UserViewLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-05
 * 설명: 게시글 조회 이력 Repository
 * ─────────────────────────────────────────────────────
 */
public interface UserViewLogRepository extends JpaRepository<UserViewLog, Long> {

    // 빈도 + 최근성 가중치 계산용 - 최근 100건
    List<UserViewLog> findTop100ByMemberIdOrderByViewedAtDesc(Long memberId);

    // 조회 제외용 - 해당 멤버가 클릭한 postId 전체 (중복 제거)
    @Query("SELECT v.postId FROM UserViewLog v WHERE v.memberId = :memberId")
    List<Long> findViewedPostIdsByMemberId(@Param("memberId") Long memberId);
}
