package com.re_form_shop_2605.controller.payment;

import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.dto.payment.PointHistoryItemDTO;
import com.re_form_shop_2605.dto.payment.PointWalletResponseDTO;
import com.re_form_shop_2605.dto.payment.WithdrawRequestDTO;
import com.re_form_shop_2605.dto.payment.WithdrawResponseDTO;
import com.re_form_shop_2605.service.payment.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 작성자: 손민정
 * 작성일: 2026-05-11
 * 설명: 포인트/출금 API
 *       - 포인트 지갑 조회, 이력 조회, 출금 요청/취소
 */

@Log4j2
@RestController
@Tag(name = "포인트/출금 API", description = "포인트 및 출금 정산 관련 API")
@RequestMapping("/api/users/me/points")
@RequiredArgsConstructor
public class PointController {
    /*
    8. 포인트 / 출금
    | GET    | `/api/users/me/points`                       | 포인트 지갑 조회
    | GET    | `/api/users/me/points/history`               | 포인트 내역 조회
    | POST   | `/api/users/me/points/withdraw`              | 출금 요청
    | GET    | `/api/users/me/points/withdraw`              | 내 출금 요청 목록
    | DELETE | `/api/users/me/points/withdraw/{withdrawId}` | 출금 요청 취소
     */
    private final PointService pointService;

    /* 1. 포인트 지갑 조회 */
    @GetMapping("")
    @Operation(summary = "포인트 지갑 조회", description = "보유 포인트, 출금 가능 포인트, 정산 대기 포인트 조회")
    public ResponseEntity<PointWalletResponseDTO> viewPointWallet(
//            @RequestParam("memberId") Long memberId
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        log.info("==== viewPointWallet 포인트 지갑 조회 ... ====");

        PointWalletResponseDTO pointWallet = pointService.getPointWallet(principal.getMemberId());

        return ResponseEntity.ok(pointWallet);
    }

    /* 2. 포인트 이력 조회 */
    @GetMapping("/history")
    @Operation(summary = "포인트 이력 조회", description = "포인트 적립/차감 이력 최신순 조회")
    public ResponseEntity<List<PointHistoryItemDTO>> viewPointHistory(
//            @RequestParam("memberId") Long memberId
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        log.info("==== viewPointHistory 포인트 이력 조회 ... ====");

        List<PointHistoryItemDTO> pointHistory = pointService.getPointHistory(principal.getMemberId());

        return ResponseEntity.ok(pointHistory);
    }

    /* 3. 출금 요청 */
    @PostMapping("/withdraw")
    @Operation(summary = "포인트 출금 신청", description = "잔액 초과 시 400 반환")
    public ResponseEntity<WithdrawResponseDTO> askWithdraw(
//            @RequestParam("memberId") Long memberId,
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestBody WithdrawRequestDTO request
    ) {
        log.info("==== askWithdraw 출금 요청 ... ====");

        WithdrawResponseDTO response = pointService.requestWithdraw(principal.getMemberId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* 4. 내 출금 요청 목록 조회 */
    @GetMapping("/withdraw")
    @Operation(summary = "내 출금 요청 목록 조회", description = "내가 신청한 출금 요청 목록 조회")
    public ResponseEntity<List<WithdrawResponseDTO>> viewRequestWithdraw(
//            @RequestParam("memberId") Long memberId
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        log.info("==== viewRequestWithdraw 사용자 출금 요청 목록 조회 ... ====");

        List<WithdrawResponseDTO> request = pointService.getMemberRequestWithdrawList(principal.getMemberId());
        return ResponseEntity.ok(request);
    }

    /* 5. 내 출금 요청 취소 */
    @DeleteMapping("/withdraw/{withdrawId}")
    @Operation(summary = "출금 요청 취소", description = "PENDING 상태인 출금 요청만 취소 가능")
    public ResponseEntity<Void> cancelRequestWithdraw(
            @PathVariable Long withdrawId,
//            @RequestParam("memberId") Long memberId
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        log.info("==== cancelRequestWithdraw 사용자 출금 요청 취소 ... ====");

        pointService.cancelWithdraw(principal.getMemberId(), withdrawId);
        return ResponseEntity.noContent().build();
    }
}