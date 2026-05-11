package com.re_form_shop_2605.controller.payment;

import com.re_form_shop_2605.dto.payment.PointHistoryItemDTO;
import com.re_form_shop_2605.dto.payment.PointWalletResponseDTO;
import com.re_form_shop_2605.dto.payment.WithdrawRequestDTO;
import com.re_form_shop_2605.dto.payment.WithdrawResponseDTO;
import com.re_form_shop_2605.service.payment.PointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@Tag(name = "포인트/출금 API", description = "포인트 및 출금 정산 관련 API")
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {
    /*
    8. 포인트 / 출금
    | GET    | `/api/points/wallet`              | 포인트 지갑 조회  |
    | GET    | `/api/points/history`             | 포인트 내역 목록  |
    | POST   | `/api/points/withdraw`            | 출금 요청       |
    | GET    | `/api/points/withdraw`            | 내 출금 요청 목록 |
    | DELETE | `/api/points/cancel/{withdrawId}` | 출금 요청 취소
     */
    private final PointService pointService;

    /* 1. 포인트 지갑 조회 */
    @GetMapping("/wallet")
    public ResponseEntity<PointWalletResponseDTO> viewPointWallet(
            @RequestParam("memberId") Long memberId
    ) {
        log.info("==== viewPointWallet 포인트 지갑 조회 ... ====");

        PointWalletResponseDTO pointWallet = pointService.getPointWallet(memberId);

        return ResponseEntity.ok(pointWallet);
    }

    /* 2. 포인트 이력 조회 */
    @GetMapping("/history")
    public ResponseEntity<List<PointHistoryItemDTO>> viewPointHistory(
            @RequestParam("memberId") Long memberId
    ) {
        log.info("==== viewPointHistory 포인트 이력 조회 ... ====");

        List<PointHistoryItemDTO> pointHistory = pointService.getPointHistory(memberId);

        return ResponseEntity.ok(pointHistory);
    }

    /* 3. 출금 요청 */
    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponseDTO> askWithdraw(
            @RequestParam("memberId") Long memberId,
            @RequestBody WithdrawRequestDTO request
    ) {
        log.info("==== askWithdraw 출금 요청 ... ====");

        WithdrawResponseDTO response = pointService.requestWithdraw(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* 4. 내 출금 요청 목록 조회 */
    @GetMapping("/withdraw")
    public ResponseEntity<List<WithdrawResponseDTO>> viewRequestWithdraw(
            @RequestParam("memberId") Long memberId
    ) {
        log.info("==== viewRequestWithdraw 사용자 출금 요청 목록 조회 ... ====");

        List<WithdrawResponseDTO> request = pointService.getMemberRequestWithdrawList(memberId);
        return ResponseEntity.ok(request);
    }

    /* 5. 내 출금 요청 취소 */
    // todo!!! Body 반환 200? 204?
    @DeleteMapping("/cancel/{withdrawId}")
    public ResponseEntity<Void> cancelRequestWithdraw(
            @PathVariable Long withdrawId,
            @RequestParam("memberId") Long memberId
    ) {
        log.info("==== cancelRequestWithdraw 사용자 출금 요청 취소 ... ====");
        pointService.cancelWithdraw(memberId, withdrawId);
        return ResponseEntity.noContent().build();
    }
}