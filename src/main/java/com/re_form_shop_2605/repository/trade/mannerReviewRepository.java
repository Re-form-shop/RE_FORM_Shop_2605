package com.re_form_shop_2605.repository.trade;

import com.re_form_shop_2605.entity.trade.MannerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 매너 리뷰 JPA 리포지토리 인터페이스
 * ─────────────────────────────────────────────────────
 */
public interface mannerReviewRepository extends JpaRepository<MannerReview, Long> {
    List<MannerReview> findTop5BySeller_MemberIdOrderByMannerIdDesc(Long memberId);

    List<MannerReview> findAllBySeller_MemberIdOrderByMannerIdDesc(Long sellerId);

    boolean existsByTrade_TradeIdAndBuyer_MemberId(Long tradeId, Long buyerId);

    @Query("select avg(review.score) from MannerReview review where review.seller.memberId = :sellerId")
    Double findAverageScoreBySellerId(@Param("sellerId") Long sellerId);
}
