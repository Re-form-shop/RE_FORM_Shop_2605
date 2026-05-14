package com.re_form_shop_2605.controller.delivery;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.delivery.DeliveryCourierListResponseDTO;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceRequestDTO;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceResponseDTO;
import com.re_form_shop_2605.service.delivery.DeliveryTrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 택배사 목록 조회와 송장번호 배송 조회 API를 제공하는 컨트롤러
 * ─────────────────────────────────────────────────────
 */
@RestController
@RequestMapping("/api/delivery/tracking")
@Tag(name = "택배 조회 API", description = "택배사 목록과 송장번호 배송 조회 관련 API")
public class DeliveryTrackingController {

    private final DeliveryTrackingService deliveryTrackingService;

    public DeliveryTrackingController(DeliveryTrackingService deliveryTrackingService) {
        this.deliveryTrackingService = deliveryTrackingService;
    }

    // 지원 가능한 택배사 목록을 조회한다.
    @Operation(
            summary = "택배사 목록 조회",
            description = "deliveryApi에서 지원하는 택배사 코드 목록을 조회합니다."
    )
    @GetMapping("/couriers")
    public ResponseEntity<ApiResponse<DeliveryCourierListResponseDTO>> readCouriers() {
        return ResponseEntity.ok(ApiResponse.ok(
                deliveryTrackingService.readCouriers(),
                "택배사 목록 조회 완료"
        ));
    }

    // 송장번호 목록 기준으로 배송 상태를 조회한다.
    @Operation(
            summary = "송장 배송 조회",
            description = "택배사 코드와 송장번호 목록으로 배송 상태를 조회합니다."
    )
    @PostMapping("/trace")
    public ResponseEntity<ApiResponse<DeliveryTrackingTraceResponseDTO>> trace(
            @Valid @RequestBody DeliveryTrackingTraceRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                deliveryTrackingService.trace(requestDTO),
                "택배 조회 완료"
        ));
    }
}
