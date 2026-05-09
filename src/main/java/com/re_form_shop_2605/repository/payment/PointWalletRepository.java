package com.re_form_shop_2605.repository.payment;

import com.re_form_shop_2605.entity.payment.PointWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointWalletRepository extends JpaRepository<PointWallet, Long> {
    /* 포인트 지갑 조회/업데이트 */
    // 1. memberId로 포인트 지갑 조회
    Optional<PointWallet> findByMemberMemberId(Long memberId);
}
