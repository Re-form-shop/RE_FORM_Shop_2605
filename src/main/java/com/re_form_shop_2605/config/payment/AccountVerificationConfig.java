package com.re_form_shop_2605.config.payment;

import com.re_form_shop_2605.service.payment.AccountVerificationService;
import com.re_form_shop_2605.service.payment.AccountVerificationServiceImpl;
import com.re_form_shop_2605.service.payment.KFTCAccountVerificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-15
 * 설명: 계좌 실명 인증 구현체 선택 Config
 *      - account.verification.type=mock -> MockAccountVerificationService (개인 테스트용)
 *      - account.verification.type=kftc -> KFTCAccountVerificationService (실제 서비스 구현체)
 * ─────────────────────────────────────────────────────
 */

@Configuration
public class AccountVerificationConfig {
    @Value("${account.verification.type}")
    private String type;

    @Bean
    public AccountVerificationService accountVerificationService(
            AccountVerificationServiceImpl mockVerificationService,
            KFTCAccountVerificationService kftcVerificationService
    ) {
        return type.equals("kftc") ? kftcVerificationService : mockVerificationService;
    }
}