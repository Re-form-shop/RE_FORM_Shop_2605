package com.re_form_shop_2605.service.member;

import com.re_form_shop_2605.dto.member.OnboardingRequestDTO;
import com.re_form_shop_2605.dto.member.OnboardingResponseDTO;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 회원 관심 설정 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
// 회원 관심 설정 서비스 인터페이스
public interface InterestSettingService {
    // 관심 설정을 저장
    void saveOnboarding(Long memberId, OnboardingRequestDTO onboardingRequestDTO);

    // 관심 설정을 조회
    OnboardingResponseDTO readOnboarding(Long memberId);
}
