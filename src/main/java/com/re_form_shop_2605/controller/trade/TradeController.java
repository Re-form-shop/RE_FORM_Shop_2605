package com.re_form_shop_2605.controller.trade;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.trade.DeliveryRequestDTO;
import com.re_form_shop_2605.dto.trade.ReviewRequestDTO;
import com.re_form_shop_2605.dto.trade.TradeRequestDTO;
import com.re_form_shop_2605.dto.trade.TradeResponseDTO;
import com.re_form_shop_2605.dto.trade.TradeStatusRequestDTO;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.service.trade.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 거래 생성과 상태 변경, 리뷰 작성 API
@Validated
@RestController
@RequestMapping("/api/trades")
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
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @RequestBody TradeRequestDTO requestDTO
    ) {
        Long tradeId = tradeService.addTrade(memberId, requestDTO);
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
    public ResponseEntity<ApiResponse<TradeResponseDTO>> readTrade(@PathVariable("id") Long tradeId) {
        return ResponseEntity.ok(ApiResponse.ok(tradeService.readTrade(tradeId), "거래 상세 조회 완료"));
    }

    // PATCH /api/trades/{id}/confirm
    // 거래를 구매 확정 상태로 변경
    @Operation(
            summary = "거래 구매 확정",
            description = "거래 ID에 해당하는 거래 상태를 구매 확정 상태로 변경합니다."
    )
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<TradeStatusOnlyResponse>> confirmTrade(@PathVariable("id") Long tradeId) {
        tradeService.modifyTradeStatus(tradeId, new TradeStatusRequestDTO(TradeStatus.CONFIRMED));
        return ResponseEntity.ok(ApiResponse.ok(new TradeStatusOnlyResponse(TradeStatus.CONFIRMED), "구매 확정 완료"));
    }

    // PATCH /api/trades/{id}/delivery
    // 거래 배송지 정보를 수정
    @Operation(
            summary = "거래 배송지 수정",
            description = "거래 ID에 해당하는 거래의 배송지 정보를 수정합니다."
    )
    @PatchMapping("/{id}/delivery")
    public ResponseEntity<ApiResponse<TradeStatusOnlyResponse>> modifyDelivery(
            @PathVariable("id") Long tradeId,
            @Valid @RequestBody DeliveryRequestDTO requestDTO
    ) {
        tradeService.modifyDelivery(tradeId, requestDTO);
        TradeResponseDTO trade = tradeService.readTrade(tradeId);
        return ResponseEntity.ok(ApiResponse.ok(new TradeStatusOnlyResponse(trade.status()), "배송지 수정 완료"));
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
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @RequestBody CreateReviewRequest request
    ) {
        Long reviewId = tradeService.addReview(
                memberId,
                new ReviewRequestDTO(tradeId, request.score(), request.comment())
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(new IdResponse(reviewId), "매너 평가 작성 완료"));
    }

    // GET /api/trades/my
    // 구매자 또는 판매자 역할 기준으로 내 거래 목록을 조회
    @Operation(
            summary = "내 거래 목록 조회",
            description = "현재 회원의 역할이 구매자인지 판매자인지에 따라 내 거래 목록을 페이지 단위로 조회합니다."
    )
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<PageResponse<TradeResponseDTO>>> readMyTrades(
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestParam(defaultValue = "buyer") String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<TradeResponseDTO> trades = "seller".equalsIgnoreCase(role)
                ? tradeService.readSellerTrades(memberId, page, size)
                : tradeService.readBuyerTrades(memberId, page, size);

        return ResponseEntity.ok(ApiResponse.ok(trades, "내 거래 목록 조회 완료"));
    }

    // 거래 생성 응답에서 사용하는 DTO
    public record CreateTradeResponse(Long tradeId, TradeStatus status) {
    }

    // 상태 변경 응답에서 사용하는 DTO
    public record TradeStatusOnlyResponse(TradeStatus status) {
    }

    // 리뷰 생성 응답에서 사용하는 DTO
    public record IdResponse(Long reviewId) {
    }

    // 리뷰 작성 요청 본문
    public record CreateReviewRequest(
            @NotNull @Min(1) @Max(5) Integer score,
            @Size(max = 500) String comment
    ) {
    }
}
