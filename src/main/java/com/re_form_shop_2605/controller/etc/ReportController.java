package com.re_form_shop_2605.controller.etc;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.etc.ReportRequestDTO;
import com.re_form_shop_2605.dto.etc.ReportResponseDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.service.etc.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 신고 등록과 내 신고 내역 조회 API
 * ─────────────────────────────────────────────────────
 */
@RestController
@RequestMapping("/api/reports")
@Tag(name = "신고 API", description = "신고 등록과 내 신고 내역 조회 관련 API")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // POST /api/reports
    // 신고 등록
    @Operation(
            summary = "신고 등록",
            description = "현재 회원이 대상 사용자 또는 게시글에 대한 신고를 등록합니다."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<IdResponse>> addReport(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody ReportRequestDTO requestDTO
    ) {
        Long reportId = reportService.addReport(principal.getMemberId(), requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(new IdResponse(reportId), "신고 접수 완료"));
    }

    // GET /api/reports/my
    // 현재 로그인 사용자의 신고 내역을 조회
    @Operation(
            summary = "내 신고 내역 조회",
            description = "현재 회원이 등록한 신고 내역을 페이지 단위로 조회합니다."
    )
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<PageResponse<ReportResponseDTO>>> readMyReports(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<ReportResponseDTO> reports = reportService.readReports(principal.getMemberId(), page, size);
        return ResponseEntity.ok(ApiResponse.ok(reports, "내 신고 목록 조회 완료"));
    }

    // 신고 등록 응답
    public record IdResponse(Long reportId) {
    }
}
