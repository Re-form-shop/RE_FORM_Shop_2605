package com.re_form_shop_2605.repository.member;

import com.re_form_shop_2605.entity.member.InterestKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 관심 키워드 JPA 리포지토리 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface InterestKeywordRepository extends JpaRepository<InterestKeyword, Long> {
    List<InterestKeyword> findAllByMember_MemberId(Long memberId);

    void deleteByMember_MemberId(Long memberId);
}
