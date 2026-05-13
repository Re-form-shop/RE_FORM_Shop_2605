package com.re_form_shop_2605.controller.payment;

import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.dto.payment.PointHistoryItemDTO;
import com.re_form_shop_2605.dto.payment.PointWalletResponseDTO;
import com.re_form_shop_2605.service.payment.PointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@Tag(name = "포인트/출금 API", description = "포인트 및 출금 정산 관련 API")
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointController {
    /*
    8. 포인트 / 출금
    | GET  | `/api/points/wallet`   | 포인트 지갑 조회  |
    | GET  | `/api/points/history`  | 포인트 내역 목록  |
    | POST | `/api/points/withdraw` | 출금 요청       |
    | GET  | `/api/points/withdraw` | 내 출금 요청 목록 |
     */
    private final PointService pointService;

    /* 1. 포인트 지갑 조회 */
    @GetMapping("/wallet")
    public ResponseEntity<PointWalletResponseDTO> viewPointWallet(
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        PointWalletResponseDTO pointWallet = pointService.getPointWallet(principal.getMemberId());

        return ResponseEntity.ok(pointWallet);
    }

    /* 2. 포인트 이력 조회 */
    @GetMapping("/history")
    public ResponseEntity<List<PointHistoryItemDTO>> viewPointHistory(
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        List<PointHistoryItemDTO> pointHistory = pointService.getPointHistory(principal.getMemberId());

        return ResponseEntity.ok(pointHistory);
    }
}
