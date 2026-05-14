package com.re_form_shop_2605.mapper.member;

import com.re_form_shop_2605.domain.member.InterestKeywordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관심 키워드 관련 매퍼 인터페이스
 * ─────────────────────────────────────────────────────
 */
@Mapper
public interface InterestKeywordMapper {
    // 관심 키워드 등록
    int insertInterestKeyword(InterestKeywordVO interestKeyword);

    // 회원 번호 기준 관심 키워드 목록 조회
    List<InterestKeywordVO> findInterestKeywordsByMemberId(@Param("memberId") Long memberId);

    // 회원 번호 기준 관심 키워드 전체 삭제
    int deleteInterestKeywordsByMemberId(@Param("memberId") Long memberId);
}
