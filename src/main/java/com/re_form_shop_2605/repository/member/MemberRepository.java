package com.re_form_shop_2605.repository.member;

import com.re_form_shop_2605.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 회원 JPA 리포지토리 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByOrderByMemberIdDesc();

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
