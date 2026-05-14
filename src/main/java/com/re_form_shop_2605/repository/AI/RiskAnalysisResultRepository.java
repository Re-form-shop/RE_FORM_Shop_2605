package com.re_form_shop_2605.repository.AI;

import com.re_form_shop_2605.entity.Enum.RiskLevel;
import com.re_form_shop_2605.entity.Enum.TargetType;
import com.re_form_shop_2605.entity.AI.RiskAnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    // 복합 인덱스 활용한 대상 조회
    Optional<RiskAnalysisResult> findByTargetTypeAndTargetId(TargetType targetType, Long targetId);

    // (게시글 / 채팅)별 목록 조회
    List<RiskAnalysisResult> findByTargetTypeOrderByCreatedAtDesc(TargetType targetType);

    /**
     * ─────────────────────────────────────────────────────
     * 작성자: 손민정
     * 작성일: 2026-05-14
     * 설명: targetType + riskLevel 기준 위험 탐지 결과 조회
     *      - 관리자 위험 게시글 목록 조회에 사용
     * ─────────────────────────────────────────────────────
     */
    /* targetType, riskLevel 따른 게시글 조회 */
    @Query("SELECT r FROM RiskAnalysisResult r WHERE r.targetType = :targetType " +
            "AND r.riskLevel IN :riskLevel ORDER BY r.createdAt DESC")
    List<RiskAnalysisResult> findByTargetTypeAndRiskLevelIn(
            @Param("targetType") TargetType targetType,
            @Param("riskLevel") List<RiskLevel> riskLevel
    );
}