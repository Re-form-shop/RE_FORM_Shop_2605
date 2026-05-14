package com.re_form_shop_2605.mapper.member;

import com.re_form_shop_2605.domain.member.MemberVO;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 회원 정보 관련 매퍼 인터페이스
 * ─────────────────────────────────────────────────────
 */
@Mapper
public interface MemberMapper {
    // 회원 등록
    int insertMember(MemberVO member);

    // 회원 번호로 회원 단건 조회
    MemberVO findMemberById(@Param("memberId") Long memberId);

    // 이메일로 회원 단건 조회
    MemberVO findMemberByEmail(@Param("email") String email);

    // 닉네임으로 회원 단건 조회
    MemberVO findMemberByNickname(@Param("nickname") String nickname);

    // 전체 회원 목록 조회
    List<MemberVO> findAllMembers();

    // 회원 기본 정보 수정
    int updateMember(MemberVO member);

    // 회원 상태 수정
    int updateMemberStatus(@Param("memberId") Long memberId, @Param("status") MemberStatus status);

    // 회원 경고 횟수 수정
    int updateWarningCount(@Param("memberId") Long memberId, @Param("warningCount") int warningCount);

}
