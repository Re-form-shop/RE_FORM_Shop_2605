package com.re_form_shop_2605.service.payment;

import com.re_form_shop_2605.dto.payment.AccountVerificationRequestDTO;
import com.re_form_shop_2605.dto.payment.AccountVerificationResponseDTO;
import org.springframework.stereotype.Service;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-15
 * 설명: 계좌 실명 인증 Mock 구현체
 *      - 금융결제원 API 연동 전 테스트용
 *      - 계좌번호 형식, 계좌주 생년월일 형식 검사만 수행
 * ─────────────────────────────────────────────────────
 */

@Service
public class AccountVerificationServiceImpl implements AccountVerificationService {
    @Override
    public AccountVerificationResponseDTO verify(AccountVerificationRequestDTO request) {
        // 조건1) accountNum이 null이거나 비어있으면 실패
        if (request.accountNum() == null || request.accountNum().isBlank()) {
            return new AccountVerificationResponseDTO(
                    false,
                    null,
                    "해당 계좌 정보가 없습니다.");
        }

        // 조건2) accountNum이 숫자가 아니면 실패
        if (!request.accountNum().matches("\\d+")) {
            return new AccountVerificationResponseDTO(
                    false,
                    null,
                    "계좌번호는 숫자만 입력 가능합니다.");
        }

        // 조건3) accountNum이 10~14자리가 아니면 실패
        if (request.accountNum().length() < 10 || request.accountNum().length() > 14) {
            return new AccountVerificationResponseDTO(
                    false,
                    null,
                    "올바른 형식의 계좌번호가 아닙니다.");
        }

        // 조건4) holderInfo가 6자리 숫자가 아니면 실패
        if (request.holderInfo().length() != 6) {
            return new AccountVerificationResponseDTO(
                    false,
                    null,
                    "계좌주의 생년월일은 6자리 형식이어야 합니다.");
        }

        // 그 외) 성공
        return new AccountVerificationResponseDTO(
                true,
                null,
                "계좌번호 실명 인증 성공!");
    }
}