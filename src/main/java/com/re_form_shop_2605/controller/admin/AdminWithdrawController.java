package com.re_form_shop_2605.controller.admin;

import com.re_form_shop_2605.dto.admin.AdminWithdrawActionRequestDTO;
import com.re_form_shop_2605.dto.payment.WithdrawResponseDTO;
import com.re_form_shop_2605.service.payment.PointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 작성자: 손민정
 * 작성일: 2026-05-11
 * 설명: 관리자 포인트 관련 기능 구현
 *       - 출금 요청 목록, 출금 승인/반려
 */

@Log4j2
@RestController
@Tag(name = "관리자 API", description = "관리자(Role.ADMIN) 관련 API)")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminWithdrawController {
    /*
    14. 관리자
    | GET   | `/api/admin/members`                       | 회원 목록 (검색·필터)
    | GET   | `/api/admin/members/{memberId}`            | 회원 상세
    | PATCH | `/api/admin/members/{memberId}/action`     | 회원 제재 (경고·정지·탈퇴)
    | GET   | `/api/admin/reports`                       | 신고 목록
    | GET   | `/api/admin/reports/{reportId}`            | 신고 상세
    | PATCH | `/api/admin/reports/{reportId}/action`     | 신고 처리
    | GET   | `/api/admin/withdraw-requests`             | 출금 요청 목록
    | PATCH | `/api/admin/withdraw-requests/{withdrawId}`| 출금 승인·반려
     */

    private final PointService pointService;

    /* 7. 전체 회원 출금 요청 목록 조회 */
    @GetMapping("/withdraw-requests")
    public ResponseEntity<List<WithdrawResponseDTO>> viewAllPendingWithdrawList() {
        List<WithdrawResponseDTO> responses = pointService.getPendingWithdrawList();

        return ResponseEntity.ok(responses);
    }

    /* 8. 출금 승인·반려 */
    @PatchMapping("/withdraw-requests/{withdrawId}")
    public ResponseEntity<WithdrawResponseDTO> withdrawalProcess(
            @PathVariable Long withdrawId,
            @RequestBody AdminWithdrawActionRequestDTO request
    ) {
        WithdrawResponseDTO response = pointService.withdrawalApprovalRejection(withdrawId, request);

        return ResponseEntity.ok(response);
    }
}
