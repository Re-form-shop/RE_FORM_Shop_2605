package com.re_form_shop_2605.service.member;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.member.*;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 회원 정보 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
// 회원 서비스 인터페이스
public interface MemberService {
    // 이메일 회원가입
    MemberResponseDTO join(MemberRequestDTO memberRequestDTO);

    // 이메일 중복 파악
    boolean isEmailDuplicate(String email);

    // 닉네임 사용 가능 여부를 확인
    NicknameResponseDTO checkNickname(String nickname);

    // 내 프로필을 조회
    ProfileResponseDTO readProfile(Long memberId);

    // 이메일로 회원 정보를 조회
    ProfileResponseDTO readEmail(String email);

    // 닉네임으로 회원 정보를 조회
    ProfileResponseDTO readNickname(String nickname);

    // 전체 회원 목록을 페이지 단위로 조회
    PageResponse<ProfileResponseDTO> readAllProfiles(int page, int size);

    // 회원 프로필 수정
    void modifyProfile(Long memberId, ProfileUpdateRequestDTO profileUpdateRequestDTO);

    // 공개 가능한 프로필 정보를 조회
    MemberPublicDTO readPublicProfile(Long memberId);

    // 회원 상태 변경
    void modifyStatus(Long memberId, MemberStatus status);

    // 회원 경고 횟수 변경
    void modifyWarningCount(Long memberId, int warningCount);

    // 회원을 탈퇴 상태로 변경
    void remove(Long memberId);
}
