/**
 * 작성자: 손민정
 * 작성일: 2026-05-11
 * 설명: 포인트/출금 API
 *       - 포인트 지갑 조회, 이력 조회, 출금 요청/취소
 */

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /* 1. 포인트 지갑 + 내역 조회 */
    @Operation(summary = "포인트 지갑 조회", description = "현재 로그인한 사용자의 포인트 잔액과 지갑 정보를 조회합니다.")
    @GetMapping("")
    public ResponseEntity<PointWalletResponseDTO> viewPointWallet(
//            @RequestParam("memberId") Long memberId
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        log.info("==== viewPointWallet 포인트 지갑 조회 ... ====");

        PointWalletResponseDTO pointWallet = pointService.getPointWallet(principal.getMemberId());

        return ResponseEntity.ok(pointWallet);
    }

    /* 2. 포인트 이력 조회 */
    @Operation(summary = "포인트 이력 조회", description = "현재 로그인한 사용자의 포인트 적립, 사용, 정산 이력을 조회합니다.")
    @GetMapping("/history")
    public ResponseEntity<List<PointHistoryItemDTO>> viewPointHistory(
//            @RequestParam("memberId") Long memberId
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        log.info("==== viewPointHistory 포인트 이력 조회 ... ====");

        List<PointHistoryItemDTO> pointHistory = pointService.getPointHistory(principal.getMemberId());

        return ResponseEntity.ok(pointHistory);
    }

    /* 3. 출금 요청 */
    @Operation(summary = "출금 요청", description = "현재 로그인한 사용자가 보유 포인트에 대해 출금 요청을 생성합니다.")
    @ApiResponse(responseCode = "201", description = "출금 요청 성공")
    @PostMapping("/withdraw")
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
    @Operation(summary = "내 출금 요청 목록 조회", description = "현재 로그인한 사용자가 요청한 출금 내역 목록을 조회합니다.")
    @GetMapping("/withdraw")
    public ResponseEntity<List<WithdrawResponseDTO>> viewRequestWithdraw(
//            @RequestParam("memberId") Long memberId
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        log.info("==== viewRequestWithdraw 사용자 출금 요청 목록 조회 ... ====");

        List<WithdrawResponseDTO> request = pointService.getMemberRequestWithdrawList(principal.getMemberId());
        return ResponseEntity.ok(request);
    }

    /* 5. 내 출금 요청 취소 */
    @Operation(summary = "출금 요청 취소", description = "현재 로그인한 사용자가 자신의 출금 요청을 취소합니다.")
    @ApiResponse(responseCode = "204", description = "출금 요청 취소 성공")
    @DeleteMapping("/withdraw/{withdrawId}")
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
