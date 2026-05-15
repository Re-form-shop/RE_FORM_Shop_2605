package com.re_form_shop_2605.service.payment;

import com.re_form_shop_2605.dto.payment.AccountVerificationRequestDTO;
import com.re_form_shop_2605.dto.payment.AccountVerificationResponseDTO;
import org.springframework.stereotype.Service;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-15
 * 설명: 금융결제원 계좌실명조회 API 연동 구현체
 *      - 실제 운영 시 교체할 클래스
 *      - 금융결제원 오픈뱅킹 2-legged Access Token 발급 후 POST /v2.0/inquiry/real_name 호출
 * ─────────────────────────────────────────────────────
 */

@Service
public class KFTCAccountVerificationService implements AccountVerificationService {
    @Override
    public AccountVerificationResponseDTO verify(AccountVerificationRequestDTO request) {
        /** 금융결제원의 이용적합성 승인 후 구현 가능
            1. WebClient로 Access Token 발급
            2. WebClient로 계좌실명조회 API 호출
            3. rsp_code == "A0000" -> verified = true, account_holder_name 반환
         */
        throw new UnsupportedOperationException("금융결제원 API 연동 후 구현 예정");
    }
}
