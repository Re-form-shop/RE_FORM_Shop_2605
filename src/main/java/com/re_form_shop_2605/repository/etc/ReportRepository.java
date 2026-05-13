package com.re_form_shop_2605.repository.etc;

import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.entity.Enum.ReportTargetType;
import com.re_form_shop_2605.entity.etc.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 작성자: 민기
 * 작성일: 2026-05-07
 * 설명: 신고 관련 JPA 리포지토리 인터페이스
 */
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByMember_MemberIdOrderByReportIdDesc(Long reporterId);

    List<Report> findAllByOrderByReportIdDesc();

    List<Report> findAllByStatusOrderByReportIdDesc(ReportStatus status);

    long countByTargetTypeAndTargetId(ReportTargetType targetType, Long targetId);
}

