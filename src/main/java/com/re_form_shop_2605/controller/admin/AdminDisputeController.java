package com.re_form_shop_2605.controller.admin;

import com.re_form_shop_2605.dto.admin.AdminDisputeActionRequestDTO;
import com.re_form_shop_2605.dto.admin.AdminDisputeDetailDTO;
import com.re_form_shop_2605.dto.admin.AdminDisputeListDTO;
import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.service.admin.AdminDisputeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
 * 설명: 관리자 분쟁 목록/상세/처리 API
 * ─────────────────────────────────────────────────────
 */
@RestController
@RequestMapping("/api/admin/disputes")
@RequiredArgsConstructor
@Tag(name = "관리자 분쟁 API", description = "관리자 분쟁 목록/상세/처리 API")
public class AdminDisputeController {

    private final AdminDisputeService adminDisputeService;

    @Operation(summary = "관리자 분쟁 목록 조회", description = "기본적으로 DISPUTED 상태를 조회하며, 처리 완료된 CONFIRMED/CANCELED/COMPLETED 분쟁도 필터로 조회할 수 있습니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AdminDisputeListDTO>>> readDisputes(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam(required = false) TradeStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        validateAdmin(principal);
        return ResponseEntity.ok(ApiResponse.ok(
                adminDisputeService.readDisputes(status, page, size),
                "관리자 분쟁 목록 조회 완료"
        ));
    }

    @Operation(summary = "관리자 분쟁 상세 조회", description = "거래 ID 기준으로 분쟁 상세를 조회합니다.")
    @GetMapping("/{tradeId}")
    public ResponseEntity<ApiResponse<AdminDisputeDetailDTO>> readDispute(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @PathVariable Long tradeId
    ) {
        validateAdmin(principal);
        return ResponseEntity.ok(ApiResponse.ok(
                adminDisputeService.readDispute(tradeId),
                "관리자 분쟁 상세 조회 완료"
        ));
    }

    @Operation(summary = "관리자 분쟁 처리", description = "구매자 분쟁 건을 기한 연장, 반려, 환불, 완료 중 하나로 처리합니다.")
    @PatchMapping("/{tradeId}")
    public ResponseEntity<ApiResponse<AdminDisputeDetailDTO>> processDispute(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @PathVariable Long tradeId,
            @Valid @RequestBody AdminDisputeActionRequestDTO requestDTO
    ) {
        validateAdmin(principal);
        return ResponseEntity.ok(ApiResponse.ok(
                adminDisputeService.processDispute(
                        tradeId,
                        requestDTO.action(),
                        requestDTO.adminMemo(),
                        requestDTO.extensionDays(),
                        principal.getNickname()
                ),
                "관리자 분쟁 처리 완료"
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
