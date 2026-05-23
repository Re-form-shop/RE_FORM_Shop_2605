package com.re_form_shop_2605.repository.etc;

import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.entity.Enum.ReportTargetType;
import com.re_form_shop_2605.entity.etc.Report;
import com.re_form_shop_2605.repository.etc.projection.ReportCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-24
 * 설명: 신고 조회 및 관리자 분쟁 보조 조회용 JPA 리포지토리
 * ─────────────────────────────────────────────────────
 */
public interface ReportRepository extends JpaRepository<Report, Long> {
    // 신고자 기준 신고 목록 최신순 조회
    List<Report> findAllByMember_MemberIdOrderByReportIdDesc(Long reporterId);

    // 전체 신고 목록 최신순 조회
    List<Report> findAllByOrderByReportIdDesc();

    // 상태별 신고 목록 최신순 조회
    List<Report> findAllByStatusOrderByReportIdDesc(ReportStatus status);

    long countByStatus(ReportStatus status);

    // 특정 대상에 대한 신고 목록 최신순 조회
    List<Report> findAllByTargetTypeAndTargetIdOrderByReportIdDesc(ReportTargetType targetType, Long targetId);

    // 특정 대상 신고 건수 조회
    long countByTargetTypeAndTargetId(ReportTargetType targetType, Long targetId);

    // 여러 판매글의 신고 건수를 한 번에 집계한다.
    @Query("""
            SELECT r.targetId AS targetId, COUNT(r) AS reportCount
            FROM Report r
            WHERE r.targetType = :targetType
              AND r.targetId IN :targetIds
            GROUP BY r.targetId
            """)
    List<ReportCountProjection> countGroupedByTargetTypeAndTargetIds(@Param("targetType") ReportTargetType targetType,
                                                                     @Param("targetIds") Collection<Long> targetIds);

    /**
     * 작성자: 손민정
     * 작성일: 2026-05-14
     * 설명: 일정 신고 건수 이상인 게시물 조회
     */
    /* 신고 접수 건수가 3건 이상 + targetType이 'POST'인 게시물(targetId = postId) 조회 */
    @Query("SELECT r.targetId FROM Report r WHERE r.targetType = :targetType " +
            "GROUP BY r.targetId HAVING COUNT(r) >= 3")
    List<Long> findTargetIdsWithReportCountOver(@Param("targetType") ReportTargetType targetType);
}
