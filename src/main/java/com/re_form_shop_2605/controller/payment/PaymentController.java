package com.re_form_shop_2605.controller.payment;

import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.dto.payment.*;
import com.re_form_shop_2605.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
/**
 * 작성자: 손민정
 * 작성일: 2026-05-09
 * 설명: 결제 API (토스페이먼츠 연동)
 *       - 결제 초기화, 승인, 조회, 취소
 */

@Log4j2
@RestController
@Tag(name = "결제 API", description = "Toss Payments 결제 관련 API")
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    /*
    7. 결제
    | POST | `/api/payments/init`               | 결제 초기화 (주문 생성) | Response: 201 Created
    | POST | `/api/payments/confirm`            | 토스 결제 승인        | Response: 200 OK
    | GET  | `/api/payments/{tradeId}`          | 결제 정보 조회        |
    | POST | `/api/payments/{paymentKey}/cancel | 결제 취소(취소, 환불)
     */
    private final PaymentService paymentService;

    /* 1. 결제 초기화 */
    @Operation(summary = "결제 초기화", description = "구매자가 결제하기 버튼 클릭 시 호출")
    @ApiResponse(responseCode = "201", description = "결제 초기화 성공")
    @PostMapping("/init")
    public ResponseEntity<PaymentInitResponseDTO> initPayment(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestBody PaymentInitRequestDTO request
    ) {
        log.info("==== initPayment 결제 초기화 주문 생성 ... ====");

        PaymentInitResponseDTO response = paymentService.createPayment(principal.getMemberId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* 2. 토스 결제 승인 */
    @Operation(summary = "결제 승인", description = "토스로부터 프론트가 결과 콜백 받아 백엔드에 승인 요청")
    @ApiResponse(responseCode = "200", description = "결제 승인 성공")
    @PostMapping("/confirm")
    public ResponseEntity<PaymentResponseDTO> confirmPayment(
            @RequestBody PaymentConfirmRequestDTO request
    ) {
        log.info("==== confirmPayment 토스 결제 승인 ... ====");

        PaymentResponseDTO response = paymentService.confirmPayment(request);

        return ResponseEntity.ok(response);
    }

    /* 3. 결제 정보 조회 */
    @Operation(summary = "결제 정보 조회", description = "tradeId로 결제 정보 조회")
    @GetMapping("/{tradeId}")
    public ResponseEntity<PaymentResponseDTO> viewPayment(@PathVariable Long tradeId) {
        log.info("==== viewPayment 결제 정보 조회 ... ====");

        PaymentResponseDTO payment = paymentService.getPayment(tradeId);
        return ResponseEntity.ok(payment);
    }

    /* 4. 결제 취소 */
    @Operation(summary = "결제 취소", description = "CANCEL : 결제 취소 | REFUND : 환불")
    @ApiResponse(responseCode = "200", description = "결제 취소 성공")
    @PostMapping("/{paymentKey}/cancel")
    public ResponseEntity<PaymentResponseDTO> cancelPayment(
            @PathVariable String paymentKey,
            @RequestBody PaymentCancelRequestDTO request
    ) {
        log.info("==== cancelPayment 결제 취소 ... ====");

        PaymentResponseDTO response = paymentService.cancelPayment(paymentKey, request);
        return ResponseEntity.ok(response);
    }
}
