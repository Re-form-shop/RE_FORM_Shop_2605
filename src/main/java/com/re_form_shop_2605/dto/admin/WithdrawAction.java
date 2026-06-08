package com.re_form_shop_2605.dto.admin;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관리자 출금 처리 액션 종류를 정의하는 ENUM 클래스
 * ─────────────────────────────────────────────────────
 */
public enum WithdrawAction {
    APPROVE,
    REJECT;

    @JsonCreator
    public static WithdrawAction from(String value) {
        if (value == null) {
            return null;
        }

        return switch (value.toUpperCase(Locale.ROOT)) {
            case "APPROVE", "APPROVED" -> APPROVE;
            case "REJECT", "REJECTED" -> REJECT;
            default -> throw new IllegalArgumentException(
                    "지원하지 않는 출금 처리 액션입니다. (APPROVE, REJECT)"
            );
        };
    }
}
