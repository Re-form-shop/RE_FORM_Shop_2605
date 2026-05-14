package com.re_form_shop_2605.service.delivery;

import com.re_form_shop_2605.dto.delivery.DeliveryCourierListResponseDTO;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceRequestDTO;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 택배 조회 API 요청을 중계하는 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryTrackingServiceImpl implements DeliveryTrackingService {

    private final DeliveryTrackingApiClient deliveryTrackingApiClient;

    // 송장번호 목록 기준으로 배송 조회를 수행한다.
    @Override
    public DeliveryTrackingTraceResponseDTO trace(DeliveryTrackingTraceRequestDTO requestDTO) {
        return deliveryTrackingApiClient.trace(requestDTO);
    }

    // 지원하는 택배사 목록을 조회한다.
    @Override
    public DeliveryCourierListResponseDTO readCouriers() {
        return deliveryTrackingApiClient.readCouriers();
    }
}
