package com.re_form_shop_2605.controller.admin;

import com.re_form_shop_2605.dto.admin.AdminReportDetailDTO;
import com.re_form_shop_2605.dto.admin.AdminReportActionRequestDTO;
import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.etc.ReportResponseDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.service.etc.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-24
 * 설명: 관리자 신고 목록/상세/처리 API
 * ─────────────────────────────────────────────────────
 */
@RestController
@RequestMapping("/api/admin/reports")
@Tag(name = "관리자 신고 API", description = "관리자의 신고 목록 조회와 처리 관련 API")
public class AdminReportController {

    // 관리자 신고 처리 서비스
    private final ReportService reportService;

    public AdminReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(
            summary = "관리자 신고 목록 조회",
            description = "관리자가 신고 목록을 상태별로 조회합니다."
    )
    @GetMapping
    // 관리자 신고 목록을 상태별로 조회한다.
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "관리자 신고 목록 조회 성공")
    })
    public ResponseEntity<ApiResponse<PageResponse<ReportResponseDTO>>> readReports(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam(required = false) ReportStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        validateAdmin(principal);
        return ResponseEntity.ok(ApiResponse.ok(
                reportService.readAllReports(status, page, size),
                "관리자 신고 목록 조회 완료"
        ));
    }

    @Operation(
            summary = "관리자 신고 상세 조회",
            description = "관리자가 신고자와 대상자 정보를 포함한 신고 상세를 조회합니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AdminReportDetailDTO>> readReport(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @PathVariable("id") Long reportId
    ) {
        validateAdmin(principal);
        return ResponseEntity.ok(ApiResponse.ok(
                reportService.readAdminReport(reportId),
                "관리자 신고 상세 조회 완료"
        ));
    }

    @Operation(
            summary = "관리자 신고 처리",
            description = "관리자가 신고 상태를 NORMAL, WARNING, DELETED 중 하나로 처리합니다."
    )
    @PatchMapping("/{id}")
    // 관리자가 신고를 처리하고 변경된 상태를 반환한다.
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "관리자 신고 처리 성공")
    })
    public ResponseEntity<ApiResponse<AdminReportDetailDTO>> processReport(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @PathVariable("id") Long reportId,
            @Valid @RequestBody AdminReportActionRequestDTO requestDTO
    ) {
        validateAdmin(principal);
        return ResponseEntity.ok(ApiResponse.ok(
                reportService.processReport(reportId, requestDTO.action(), requestDTO.adminMemo(), principal.getNickname()),
                "관리자 신고 처리 완료"
        ));
    }

    // 현재 로그인 사용자가 관리자 권한을 가졌는지 확인
    private void validateAdmin(MemberSecurityDTO principal) {
        boolean isAdmin = principal.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        if (!isAdmin) {
            throw new IllegalArgumentException("관리자만 접근할 수 있습니다.");
        }
    }
}
