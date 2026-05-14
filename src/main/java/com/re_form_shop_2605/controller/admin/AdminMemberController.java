package com.re_form_shop_2605.controller.admin;

import com.re_form_shop_2605.dto.admin.AdminMemberDetailDTO;
import com.re_form_shop_2605.dto.admin.AdminMemberListDTO;
import com.re_form_shop_2605.dto.admin.AdminMemberRequestDTO;
import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.service.admin.AdminMemberService;
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
 * 작성일: 2026-05-12
 * 설명: 관리자 권한으로 회원 목록과 상세를 조회하고 회원 제재를 처리하는 API를 제공
 * ─────────────────────────────────────────────────────
 */
@RestController
@RequestMapping("/api/admin/members")
@Tag(name = "관리자 회원 API", description = "관리자의 회원 조회 및 제재 관련 API")
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    //  관리자 회원 API에서 사용할 서비스를 주입받는다.
    public AdminMemberController(AdminMemberService adminMemberService) {
        this.adminMemberService = adminMemberService;
    }

    @Operation(
            summary = "관리자 회원 목록 조회",
            description = "관리자가 키워드와 상태 조건으로 회원 목록을 조회합니다."
    )
    @GetMapping
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "관리자 회원 목록 조회 성공")
    })
    public ResponseEntity<ApiResponse<PageResponse<AdminMemberListDTO>>> readMembers(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) MemberStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        validateAdmin(principal);
        return ResponseEntity.ok(ApiResponse.ok(
                adminMemberService.readMembers(keyword, status, page, size),
                "관리자 회원 목록 조회 완료"
        ));
    }

    @Operation(
            summary = "관리자 회원 상세 조회",
            description = "관리자가 특정 회원의 상세 정보와 관련 현황을 조회"
    )
    @GetMapping("/{id}")
    // 관리자 권한으로 특정 회원의 상세 정보를 조회한다.
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "관리자 회원 상세 조회 성공")
    })
    public ResponseEntity<ApiResponse<AdminMemberDetailDTO>> readMember(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @PathVariable("id") Long memberId
    ) {
        validateAdmin(principal);
        return ResponseEntity.ok(ApiResponse.ok(
                adminMemberService.readMember(memberId),
                "관리자 회원 상세 조회 완료"
        ));
    }

    @Operation(
            summary = "관리자 회원 제재 처리",
            description = "관리자가 회원에게 경고, 정지, 탈퇴 처리를 적용합니다."
    )
    @PatchMapping("/{id}/action")
    //  관리자 권한으로 회원 제재 처리
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "관리자 회원 처리 성공")
    })
    public ResponseEntity<ApiResponse<AdminMemberDetailDTO>> processMember(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @PathVariable("id") Long memberId,
            @Valid @RequestBody AdminMemberRequestDTO requestDTO
    ) {
        validateAdmin(principal);
        return ResponseEntity.ok(ApiResponse.ok(
                adminMemberService.processMember(memberId, requestDTO),
                "관리자 회원 처리 완료"
        ));
    }

    // 현재 로그인 사용자가 관리자 권한을 가졌는지 검증
    private void validateAdmin(MemberSecurityDTO principal) {
        boolean isAdmin = principal.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        if (!isAdmin) {
            throw new IllegalArgumentException("관리자만 접근할 수 있습니다.");
        }
    }
}
