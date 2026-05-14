package com.re_form_shop_2605.controller.trade;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceResponseDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.dto.trade.DeliveryRequestDTO;
import com.re_form_shop_2605.dto.trade.ReviewRequestDTO;
import com.re_form_shop_2605.dto.trade.TradeRequestDTO;
import com.re_form_shop_2605.dto.trade.TradeResponseDTO;
import com.re_form_shop_2605.dto.trade.TradeShippingRequestDTO;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.service.trade.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 거래 생성과 상태 변경, 리뷰 작성 API
 * ─────────────────────────────────────────────────────
 */
@Validated
@RestController
@RequestMapping("/api/trades")
@Tag(name = "거래 API", description = "거래 생성, 상태 변경, 리뷰 작성 관련 API")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    // POST /api/trades
    // 판매글을 기준으로 새 거래를 생성
    @Operation(
            summary = "거래 생성",
            description = "판매글 정보를 바탕으로 구매자와 판매자 간의 새 거래를 생성합니다."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<CreateTradeResponse>> addTrade(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody TradeRequestDTO requestDTO
    ) {
        Long tradeId = tradeService.addTrade(principal.getMemberId(), requestDTO);
        TradeResponseDTO trade = tradeService.readTrade(tradeId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(new CreateTradeResponse(tradeId, trade.status()), "거래 생성 완료"));
    }

    // GET /api/trades/{id}
    // 거래 상세 정보를 조회
    @Operation(
            summary = "거래 상세 조회",
            description = "거래 ID로 거래 상태, 배송지, 참여자 정보를 포함한 상세 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TradeResponseDTO>> readTrade(
            @PathVariable("id") Long tradeId,
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                tradeService.readTrade(principal.getMemberId(), tradeId),
                "거래 상세 조회 완료"
        ));
    }

    // PATCH /api/trades/{id}/accept
    // 판매자가 거래 요청을 수락
    @Operation(
            summary = "거래 요청 수락",
            description = "판매자가 거래 ID에 해당하는 구매 요청을 수락하고 거래를 진행 상태로 전환합니다."
    )
    @PatchMapping("/{id}/accept")
    public ResponseEntity<ApiResponse<TradeStatusOnlyResponse>> acceptTrade(
            @PathVariable("id") Long tradeId,
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        tradeService.acceptTrade(principal.getMemberId(), tradeId);
        return ResponseEntity.ok(ApiResponse.ok(new TradeStatusOnlyResponse(TradeStatus.ACCEPTED), "거래 요청 수락 완료"));
    }

    // PATCH /api/trades/{id}/confirm
    // 거래를 구매 확정 상태로 변경
    @Operation(
            summary = "거래 구매 확정",
            description = "거래 ID에 해당하는 거래 상태를 구매 확정 상태로 변경합니다."
    )
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<TradeStatusOnlyResponse>> confirmTrade(
            @PathVariable("id") Long tradeId,
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        tradeService.confirmTrade(principal.getMemberId(), tradeId);
        return ResponseEntity.ok(ApiResponse.ok(new TradeStatusOnlyResponse(TradeStatus.CONFIRMED), "구매 확정 완료"));
    }

    // PATCH /api/trades/{id}/delivery
    // 거래 수령 주소 정보를 수정
    @Operation(
            summary = "거래 수령 주소 수정",
            description = "택배 거래는 구매자가 배송지를, 직거래는 판매자가 만남 주소를 수정합니다."
    )
    @PatchMapping("/{id}/delivery")
    public ResponseEntity<ApiResponse<TradeStatusOnlyResponse>> modifyDelivery(
            @PathVariable("id") Long tradeId,
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody DeliveryRequestDTO requestDTO
    ) {
        tradeService.modifyDelivery(principal.getMemberId(), tradeId, requestDTO);
        TradeResponseDTO trade = tradeService.readTrade(tradeId);
        return ResponseEntity.ok(ApiResponse.ok(new TradeStatusOnlyResponse(trade.status()), "배송지 수정 완료"));
    }

    // PATCH /api/trades/{id}/shipping
    // 판매자 택배사와 송장번호를 입력
    @Operation(
            summary = "배송 정보 입력",
            description = "판매자가 택배사와 송장번호를 입력해 배송 추적을 시작합니다."
    )
    @PatchMapping("/{id}/shipping")
    public ResponseEntity<ApiResponse<TradeStatusOnlyResponse>> startShipping(
            @PathVariable("id") Long tradeId,
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody TradeShippingRequestDTO requestDTO
    ) {
        tradeService.startShipping(principal.getMemberId(), tradeId, requestDTO);
        TradeResponseDTO trade = tradeService.readTrade(tradeId);
        return ResponseEntity.ok(ApiResponse.ok(new TradeStatusOnlyResponse(trade.status()), "배송 정보 입력 완료"));
    }

    // GET /api/trades/{id}/tracking
    // 판매자 구매자 모두 송장 번호로 배송 추적 조회
    @Operation(
            summary = "거래 배송 조회",
            description = "거래 참여자가 등록된 송장번호로 배송 상태를 조회합니다."
    )
    @GetMapping("/{id}/tracking")
    public ResponseEntity<ApiResponse<DeliveryTrackingTraceResponseDTO>> readTracking(
            @PathVariable("id") Long tradeId,
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                tradeService.readTradeTracking(principal.getMemberId(), tradeId),
                "거래 배송 조회 완료"
        ));
    }

    // POST /api/trades/{id}/reviews
    // 거래에 대한 매너 리뷰를 등록
    @Operation(
            summary = "거래 리뷰 등록",
            description = "거래 완료 후 상대방에 대한 매너 점수와 후기를 등록합니다."
    )
    @PostMapping("/{id}/reviews")
    public ResponseEntity<ApiResponse<IdResponse>> addReview(
            @PathVariable("id") Long tradeId,
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody CreateReviewRequest request
    ) {
        Long reviewId = tradeService.addReview(
                principal.getMemberId(),
                new ReviewRequestDTO(tradeId, request.score(), request.comment())
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(new IdResponse(reviewId), "매너 평가 작성 완료"));
    }

    // GET /api/trades/my
    // 구매자 또는 판매자 역할 기준으로 내 거래 목록을 조회
    @Operation(
            summary = "내 거래 목록 조회",
            description = "현재 회원의 구매/판매 거래 목록을 역할과 상태 조건에 따라 페이지 단위로 조회합니다."
    )
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<PageResponse<TradeResponseDTO>>> readMyTrades(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam(defaultValue = "buyer") String role,
            @RequestParam(required = false) TradeStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<TradeResponseDTO> trades = switch (role.toLowerCase()) {
            case "seller" -> tradeService.readSellerTrades(principal.getMemberId(), status, page, size);
            case "buyer" -> tradeService.readBuyerTrades(principal.getMemberId(), status, page, size);
            default -> throw new IllegalArgumentException("role은 buyer 또는 seller만 사용할 수 있습니다.");
        };

        return ResponseEntity.ok(ApiResponse.ok(trades, "내 거래 목록 조회 완료"));
    }

    public record CreateTradeResponse(Long tradeId, TradeStatus status) {
    }

    public record TradeStatusOnlyResponse(TradeStatus status) {
    }

    public record IdResponse(Long reviewId) {
    }

    // 리뷰 작성 요청 본문
    public record CreateReviewRequest(
            @NotNull @Min(1) @Max(5) Integer score,
            @Size(max = 500) String comment
    ) {
    }
}
