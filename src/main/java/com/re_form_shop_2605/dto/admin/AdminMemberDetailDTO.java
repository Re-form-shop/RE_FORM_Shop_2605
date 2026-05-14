package com.re_form_shop_2605.dto.admin;

import com.re_form_shop_2605.dto.etc.ReportResponseDTO;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관리자 회원 상세 조회 DTO
 * ─────────────────────────────────────────────────────
 */
public record AdminMemberDetailDTO(
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
        // 회원 상태
        MemberStatus status,
        // 경고 횟수
        int warningCount,
        // 매너 평균 점수
        BigDecimal mannerScore,
        // 권한
        Role role,
        // 가입일
        LocalDateTime createdAt,
        // 해당 회원 관련 신고 내역
        List<ReportResponseDTO> receivedReports,
        // 총 판매 건수
        int totalSales,
        // 총 구매 건수
        int totalPurchases
) {
}
