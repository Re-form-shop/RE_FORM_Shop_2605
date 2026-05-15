package com.re_form_shop_2605.service.payment;

import com.re_form_shop_2605.dto.payment.AccountVerificationRequestDTO;
import com.re_form_shop_2605.dto.payment.AccountVerificationResponseDTO;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-15
 * 설명: 계좌 실명 인증 서비스 인터페이스
 *      - Mock (금융결제원 구현체 교체 가능하도록 추상화)
 * ─────────────────────────────────────────────────────
 */

public interface AccountVerificationService {
    AccountVerificationResponseDTO verify(AccountVerificationRequestDTO request);
}