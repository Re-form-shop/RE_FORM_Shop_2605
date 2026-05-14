package com.re_form_shop_2605.mapper.member;

import com.re_form_shop_2605.domain.member.InterestSettingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관심 종목 관련 매퍼 인터페이스
 * ─────────────────────────────────────────────────────
 */
@Mapper
public interface InterestSettingMapper {

    // 회원 관심 종목 및 구단 등록
    int insertInterestSetting(InterestSettingVO interestSetting);

    // 회원 번호 기준 관심 종목 및 구단 조회
    InterestSettingVO findInterestSettingByMemberId(@Param("memberId") Long memberId);

    // 회원 관심 종목 및 구단 수정
    int updateInterestSetting(InterestSettingVO interestSetting);
}
