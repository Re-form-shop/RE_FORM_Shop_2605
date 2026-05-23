package com.re_form_shop_2605.service.admin;

import com.re_form_shop_2605.dto.admin.AdminMemberDetailDTO;
import com.re_form_shop_2605.dto.admin.AdminMemberListDTO;
import com.re_form_shop_2605.dto.admin.AdminMemberActionRequestDTO;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.entity.Enum.MemberStatus;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 관리자 회원 목록 조회, 상세 조회, 제재 처리를 담당하는 서비스 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface AdminMemberService {

    // 신고 회원 목록 조회
    PageResponse<AdminMemberListDTO> readMembers(String keyword, MemberStatus status, int page, int size);

    // 신고 회원 상세
    AdminMemberDetailDTO readMember(Long memberId);

    // 신고 회원 상태 처리
    AdminMemberDetailDTO processMember(Long memberId, AdminMemberActionRequestDTO requestDTO);
}
