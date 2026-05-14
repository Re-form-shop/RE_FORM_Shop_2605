package com.re_form_shop_2605.domain.member;

import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.Role;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 회원 정보 mybatis 전용 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
    private Long member_id; // 회원 id
    private String email; // 회원 이메일
    private String password; // 회원 비밀번호
    private String nickname; // 회원 닉네임
    private String profileImageUrl; // 회원 프로필 이미지 url
    private String bio; // 회원 자기소개
    private BigDecimal mannerScore; // 매너 평균 점수
    private Role role; // 사용자 분류(유저, 관리자)
    private MemberStatus status; // 활동상태 (활성화/정지/탈퇴)
    private int warningCount; // 누적 경고 횟수
    private boolean emailEvent; // 이벤트 이메일 수신 여부
    private LocalDateTime createdAt; // 생성일
}
