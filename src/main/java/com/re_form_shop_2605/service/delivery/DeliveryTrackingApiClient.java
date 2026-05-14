package com.re_form_shop_2605.service.delivery;

import com.re_form_shop_2605.dto.delivery.DeliveryCourierListResponseDTO;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceRequestDTO;
import com.re_form_shop_2605.dto.delivery.DeliveryTrackingTraceResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: deliveryapi.co.kr 택배 조회 API를 호출하는 외부 연동 클라이언트
 * ─────────────────────────────────────────────────────
 */
@Component
public class DeliveryTrackingApiClient {

    private final WebClient webClient;
    private final String apiKey;
    private final String secretKey;

    // 설정에 저장된 인증 키를 사용해 택배 조회용 WebClient를 구성한다.
    public DeliveryTrackingApiClient(
            @Value("${deliveryApi.base-url:${deleveryApi.base-url:${deleveryApi.base-url:https://api.deliveryapi.co.kr}}}") String baseUrl,
            @Value("${deliveryApi.api-key:${deleveryApi.API-Key:${deleveryApi.API-Key:}}}") String apiKey,
            @Value("${deliveryApi.secret-key:${deleveryApi.Secret-Key:${deleveryApi.Secret-Key:}}}") String secretKey
    ) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey + ":" + secretKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    // 송장번호 목록 기준으로 택배 배송 정보를 조회한다.
    public DeliveryTrackingTraceResponseDTO trace(DeliveryTrackingTraceRequestDTO requestDTO) {
        validateApiCredentials();
        try {
            return webClient.post()
                    .uri("/v1/tracking/trace")
                    .bodyValue(requestDTO)
                    .retrieve()
                    .bodyToMono(DeliveryTrackingTraceResponseDTO.class)
                    .block();
        } catch (WebClientResponseException exception) {
            throw new IllegalArgumentException("택배 조회 API 호출 실패: " + exception.getResponseBodyAsString(), exception);
        }
    }

    // 지원하는 택배사 코드 목록을 조회한다.
    public DeliveryCourierListResponseDTO readCouriers() {
        validateApiCredentials();
        try {
            return webClient.get()
                    .uri("/v1/tracking/couriers")
                    .retrieve()
                    .bodyToMono(DeliveryCourierListResponseDTO.class)
                    .block();
        } catch (WebClientResponseException exception) {
            throw new IllegalArgumentException("택배사 목록 조회 API 호출 실패: " + exception.getResponseBodyAsString(), exception);
        }
    }

    // 택배조회 API 인증 키 설정이 비어 있지 않은지 검증한다.
    private void validateApiCredentials() {
        if (apiKey == null || apiKey.isBlank() || secretKey == null || secretKey.isBlank()) {
            throw new IllegalStateException("택배조회 API 인증 키가 설정되지 않았습니다.");
        }
    }
}
