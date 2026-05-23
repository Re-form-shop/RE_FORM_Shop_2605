package com.re_form_shop_2605.controller.admin;

import com.re_form_shop_2605.dto.admin.AdminWithdrawActionRequestDTO;
import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.dto.payment.WithdrawResponseDTO;
import com.re_form_shop_2605.service.payment.PointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-24
 * 설명: 관리자 출금 요청 조회/처리 API
 * ─────────────────────────────────────────────────────
 */
@Log4j2
@RestController
@Tag(name = "관리자 API", description = "관리자(Role.ADMIN) 관련 API)")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminWithdrawController {

    // 출금 관리자 서비스
    private final PointService pointService;

    // 전체 회원 출금 요청 목록 조회
    @GetMapping("/withdraw-requests")
    public ResponseEntity<ApiResponse<List<WithdrawResponseDTO>>> viewAllPendingWithdrawList(
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        validateAdmin(principal);
        List<WithdrawResponseDTO> responses = pointService.getPendingWithdrawList();

        return ResponseEntity.ok(ApiResponse.ok(responses, "출금 요청 목록 조회 완료"));
    }

    // 출금 승인 또는 반려 처리
    @PatchMapping("/withdraw-requests/{withdrawId}")
    public ResponseEntity<ApiResponse<WithdrawResponseDTO>> withdrawalProcess(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @PathVariable Long withdrawId,
            @RequestBody AdminWithdrawActionRequestDTO request
    ) {
        validateAdmin(principal);
        WithdrawResponseDTO response = pointService.withdrawalApprovalRejection(withdrawId, request);

        return ResponseEntity.ok(ApiResponse.ok(response, "출금 요청 처리 완료"));
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
