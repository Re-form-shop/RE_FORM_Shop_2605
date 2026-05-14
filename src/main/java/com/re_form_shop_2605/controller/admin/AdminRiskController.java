package com.re_form_shop_2605.controller.admin;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.etc.RiskAnalysisResultDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.entity.Enum.RiskLevel;
import com.re_form_shop_2605.service.etc.RiskAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-14
 * 설명: 관리자 위험 탐지 조회
 * riskLevel 필터로 LOW | MID | HIGH 구분 조회
 * ADMIN 권한 필요
 * ─────────────────────────────────────────────────────
 */
@Log4j2
@RestController
@RequestMapping("/api/admin/risk")
@Tag(name = "관리자 위험 탐지 API", description = "AI 위험 탐지 배치 분석 결과 조회 API (게시글 / 채팅)")
@RequiredArgsConstructor
public class AdminRiskController {
    private final RiskAnalysisService riskAnalysisService;

    // GET /api/admin/risk/posts
    @Operation(
            summary = "위험 게시글 목록 조회",
            description = "관리자가 유해성 감지된 게시글 내역을 riskLevel로 필터링하여 조회합니다."
    )
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<PageResponse<RiskAnalysisResultDTO>>> viewRiskDetectionResult(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam(required = false) RiskLevel riskLevel,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("==== viewRiskDetectionResult 판매 게시글 위험 탐지 중 ... ====");

        validateAdmin(principal);

        return ResponseEntity.ok(ApiResponse.ok(riskAnalysisService.readPostRiskList(riskLevel, page, size),
                "위험 게시글 목록 조회 완료"
        ));
    }

    // GET /api/admin/risk/chat
    @Operation(
            summary = "위험 채팅 목록 조회",
            description = "관리자가 유해성 감지된 채팅 내역을 riskLevel로 필터링하여 조회합니다."
    )
    @GetMapping("/chat")
    public ResponseEntity<ApiResponse<PageResponse<RiskAnalysisResultDTO>>> viewChatRiskDetectionResult(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam(required = false) RiskLevel riskLevel,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("==== viewChatRiskDetectionResult 채팅 위험 탐지 중 ... ====");

        validateAdmin(principal);

        return ResponseEntity.ok(ApiResponse.ok(riskAnalysisService.readChatRiskList(riskLevel, page, size),
                "위험 채팅 목록 조회 완료"
        ));
    }

    // 관리자 접근 권환 확인 메서드
    private void validateAdmin(MemberSecurityDTO principal) {
        boolean isAdmin = principal.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        if (!isAdmin) {
            throw new IllegalArgumentException("관리자만 접근할 수 있습니다.");
        }
    }
}
