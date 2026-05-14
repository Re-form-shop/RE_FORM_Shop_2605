package com.re_form_shop_2605.repository.payment;

import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.payment.PointWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-11
 * 설명: 포인트 지갑 조회 및 업데이트 Repository
 * ─────────────────────────────────────────────────────
 */

@Repository
public interface PointWalletRepository extends JpaRepository<PointWallet, Long> {
    /* 포인트 지갑 조회/업데이트 */
    // 1. memberId로 포인트 지갑 조회
    Optional<PointWallet> findByMemberMemberId(Long memberId);

    Long member(Member member);
}
