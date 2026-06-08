package com.re_form_shop_2605.controller.admin;

import com.re_form_shop_2605.dto.admin.AdminDashboardSummaryDTO;
import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.service.admin.AdminDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-23
 * 설명: 관리자 대시보드 집계 API
 * ─────────────────────────────────────────────────────
 */
@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
@Tag(name = "관리자 대시보드 API", description = "관리자 대시보드 집계 조회 API")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    @Operation(summary = "관리자 대시보드 요약 조회", description = "현재 구현된 관리자 목록 API들을 집계한 요약 정보를 반환합니다.")
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<AdminDashboardSummaryDTO>> readSummary(
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        validateAdmin(principal);
        return ResponseEntity.ok(ApiResponse.ok(
                adminDashboardService.readSummary(),
                "관리자 대시보드 요약 조회 완료"
        ));
    }

    private void validateAdmin(MemberSecurityDTO principal) {
        boolean isAdmin = principal.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        if (!isAdmin) {
            throw new IllegalArgumentException("관리자만 접근할 수 있습니다.");
        }
    }
}
