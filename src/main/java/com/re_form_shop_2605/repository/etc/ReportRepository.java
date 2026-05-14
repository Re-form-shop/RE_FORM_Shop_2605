package com.re_form_shop_2605.repository.etc;

import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.entity.Enum.ReportTargetType;
import com.re_form_shop_2605.entity.etc.Report;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-07
 * 설명: 신고 관련 JPA 리포지토리 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByMember_MemberIdOrderByReportIdDesc(Long reporterId);

    List<Report> findAllByOrderByReportIdDesc();

    List<Report> findAllByStatusOrderByReportIdDesc(ReportStatus status);

    long countByTargetTypeAndTargetId(ReportTargetType targetType, Long targetId);

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
