package com.re_form_shop_2605.dto.member;

import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 마이페이지 내 프로필 조회 응답 DTO
 * ─────────────────────────────────────────────────────
 */
public record ProfileResponseDTO(
        // 회원 번호
        Long memberId,
        // 이메일
        String email,
        // 닉네임
        String nickname,
        // 프로필 이미지 URL
        String profileImageUrl,
        // 자기소개
        String bio,
        // 매너 평균 점수
        BigDecimal mannerScore,
        // 권한
        Role role,
        // 회원 상태
        MemberStatus status,
        // 총 보유 포인트
        int pointBalance,
        // 출금 가능 포인트
        int pointWithdrawable,
        // 정산 대기 포인트
        int pointPending,
        // 완료된 판매 건수
        int totalSales,
        // 완료된 구매 건수
        int totalPurchases,
        // 관심 정보
        OnboardingResponseDTO interest,
        // 가입일
        LocalDateTime createdAt
) {
}
