package com.re_form_shop_2605.service.trade;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceResponseDTO;
import com.re_form_shop_2605.dto.trade.DeliveryRequestDTO;
import com.re_form_shop_2605.dto.trade.ReviewRequestDTO;
import com.re_form_shop_2605.dto.trade.ReviewResponseDTO;
import com.re_form_shop_2605.dto.trade.TradeRequestDTO;
import com.re_form_shop_2605.dto.trade.TradeResponseDTO;
import com.re_form_shop_2605.dto.trade.TradeShippingRequestDTO;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 거래 도메인 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
// 거래 관련 서비스 인터페이스
public interface TradeService {

    // 거래 생성
    Long addTrade(Long buyerId, TradeRequestDTO tradeRequestDTO);

    // 거래 단건 조회
    TradeResponseDTO readTrade(Long tradeId);

    // 거래 참여자 기준 단건 조회
    TradeResponseDTO readTrade(Long requesterId, Long tradeId);

    // 구매자 기준 거래 목록 조회
    PageResponse<TradeResponseDTO> readBuyerTrades(Long buyerId, TradeStatus status, int page, int size);

    // 판매자 기준 거래 목록 조회
    PageResponse<TradeResponseDTO> readSellerTrades(Long sellerId, TradeStatus status, int page, int size);

    // 판매자가 거래 요청을 수락
    void acceptTrade(Long sellerId, Long tradeId);

    // 구매자가 거래를 구매 확정 처리
    void confirmTrade(Long buyerId, Long tradeId);

    // 수령 주소 정보 수정
    void modifyDelivery(Long requesterId, Long tradeId, DeliveryRequestDTO deliveryRequestDTO);

    // 판매자가 배송 정보 입력
    void startShipping(Long sellerId, Long tradeId, TradeShippingRequestDTO requestDTO);

    // 거래 기준 배송 조회
    DeliveryTrackingTraceResponseDTO readTradeTracking(Long requesterId, Long tradeId);

    // 매너 리뷰 등록
    Long addReview(Long buyerId, ReviewRequestDTO reviewRequestDTO);

    // 매너 리뷰 단건 조회
    ReviewResponseDTO readReview(Long mannerId);

    // 판매자 기준 매너 리뷰 목록 조회
    PageResponse<ReviewResponseDTO> readSellerReviews(Long sellerId, int page, int size);
}
