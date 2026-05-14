package com.re_form_shop_2605.repository.member;

import com.re_form_shop_2605.entity.member.SocialMember;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 소셜 회원 JPA 리포지토리 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface socialMemberRepository extends JpaRepository<SocialMember, Long> {
}
