package com.re_form_shop_2605.repository.etc;

import com.re_form_shop_2605.entity.Enum.TargetType;
import com.re_form_shop_2605.entity.etc.RiskAnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-13
 * 설명: 위험 탐지 목록 Repository
 * ─────────────────────────────────────────────────────
 */
public interface RiskAnalysisResultRepository extends JpaRepository<RiskAnalysisResult, Long> {
    Optional<RiskAnalysisResult> findByTargetTypeAndTargetId(TargetType targetType, Long targetId);

    // (게시글 / 채팅)별 목록 조회
    List<RiskAnalysisResult> findByTargetTypeOrderByCreatedAtDesc(TargetType targetType);
}
