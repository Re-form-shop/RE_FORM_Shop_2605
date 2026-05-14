package com.re_form_shop_2605.service.etc;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.etc.ReportRequestDTO;
import com.re_form_shop_2605.dto.etc.ReportResponseDTO;
import com.re_form_shop_2605.entity.Enum.ReportStatus;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 신고 기능을 제공하는 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface ReportService {
    // 신고를 등록
    Long addReport(Long reporterId, ReportRequestDTO reportRequestDTO);

    // 신고를 단건 조회
    ReportResponseDTO readReport(Long reportId);

    // 신고 목록을 페이지 단위로 조회
    PageResponse<ReportResponseDTO> readReports(Long reporterId, int page, int size);

    // 관리자 기준으로 전체 신고 목록을 조회
    PageResponse<ReportResponseDTO> readAllReports(ReportStatus status, int page, int size);

    // 관리자가 신고 처리 상태를 변경
    ReportResponseDTO processReport(Long reportId, ReportStatus action);
}
