package com.re_form_shop_2605.mapper.etc;

import com.re_form_shop_2605.domain.etc.ReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 신고 관련 매퍼 인터페이스
 * ─────────────────────────────────────────────────────
 */
@Mapper
public interface ReportMapper {
    // 신고 등록
    int insertReport(ReportVO report);

    // 신고 번호로 단건 조회
    ReportVO findReportById(@Param("reportId") Long reportId);

    // 신고자 회원 번호 기준 신고 목록 조회
    List<ReportVO> findReportsByReporterId(@Param("reporterId") Long reporterId);

}
