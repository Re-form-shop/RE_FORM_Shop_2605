package com.re_form_shop_2605.service.delivery;

import com.re_form_shop_2605.dto.delivery.DeliveryCourierListResponseDTO;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceRequestDTO;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceResponseDTO;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 택배 조회 API 기능을 제공하는 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface DeliveryTrackingService {

    // 송장번호 목록 기준 배송 조회
    DeliveryTrackingTraceResponseDTO trace(DeliveryTrackingTraceRequestDTO requestDTO);

    // 지원하는 택배사 목록 조회
    DeliveryCourierListResponseDTO readCouriers();
}
