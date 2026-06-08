package com.re_form_shop_2605.repository.AI;

import com.re_form_shop_2605.entity.AI.UserSearchLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-05
 * 설명: 검색 키워드 이력 Repository
 * ─────────────────────────────────────────────────────
 */
public interface UserSearchLogRepository extends JpaRepository<UserSearchLog, Long> {

    // 빈도 + 최근성 가중치 계산용 - 최근 50건
    List<UserSearchLog> findTop50ByMemberIdOrderBySearchedAtDesc(Long memberId);
}
