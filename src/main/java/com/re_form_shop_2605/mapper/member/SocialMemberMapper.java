package com.re_form_shop_2605.mapper.member;

import com.re_form_shop_2605.domain.member.SocialMemberVO;
import com.re_form_shop_2605.entity.Enum.Provider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 소셜 회원 관련 매퍼 인터페이스
 * ─────────────────────────────────────────────────────
 */
@Mapper
public interface SocialMemberMapper {

    // 소셜 로그인 회원 등록
    int insertSocialMember(SocialMemberVO socialMember);

    // 소셜 로그인 번호로 단건 조회
    SocialMemberVO findSocialMemberById(Long socialId);

    // 회원 번호와 소셜 제공자 기준 단건 조회
    SocialMemberVO findSocialMemberByMemberIdAndProvider(@Param("memberId") Long memberId,
                                                         @Param("provider") Provider provider);

    // 소셜 제공자와 제공자 회원 번호 기준 단건 조회
    SocialMemberVO findSocialMemberByProviderAndProviderId(@Param("provider") Provider provider,
                                                           @Param("providerId") String providerId);

    // 일반 회원 번호 기준 소셜 로그인 목록 조회
    List<SocialMemberVO> findSocialMembersByMemberId(Long memberId);

    // 소셜 로그인 번호 기준 삭제
    int deleteSocialMember(Long socialId);

    // 일반 회원 번호 기준 소셜 로그인 전체 삭제
    int deleteSocialMembersByMemberId(Long memberId);
}
