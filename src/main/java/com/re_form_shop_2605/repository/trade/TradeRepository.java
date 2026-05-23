package com.re_form_shop_2605.repository.trade;

import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.entity.trade.Trade;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 거래 JPA 리포지토리 인터페이스
 * ─────────────────────────────────────────────────────
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {
    /**
     * ─────────────────────────────────────────────────────
     * 작성자: 손민정
     * 작성일: 2026-05-09
     * 설명: 거래 조회 Repository
     * ─────────────────────────────────────────────────────
     */
    /* 거래 상태 변경 */
    // 1. TradeStatus 따른 거래 조회
    // 용도 1) 미정산 거래 조회 (status = CONFIRMED && trade_id.point_history IS NULL)
    @Query("SELECT t FROM Trade t WHERE t.status = :status " +
            "AND NOT EXISTS (SELECT p FROM PointHistory p WHERE p.trade = t)")
    List<Trade> findConfirmedUnsettledTrades(@Param("status")TradeStatus status);
    long countByStatus(TradeStatus status);
    int countBySeller_MemberIdAndStatus(Long memberId, TradeStatus status);

    int countByBuyer_MemberIdAndStatus(Long memberId, TradeStatus status);

    long countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(LocalDateTime startInclusive, LocalDateTime endExclusive);

    long countByCompletedAtGreaterThanEqualAndCompletedAtLessThan(LocalDateTime startInclusive, LocalDateTime endExclusive);

    long countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(TradeStatus status, LocalDateTime startInclusive, LocalDateTime endExclusive);

    // 2. 자동 구매 확정 처리 대상 조회
    @Query("SELECT t FROM Trade t WHERE t.status = :status " +
            "AND t.receivedAt <= :dueDate")

    /**
     * ─────────────────────────────────────────────────────
     * 작성자: 김민기
     * 작성일: 2026-05-08
     * 설명: 거래 JPA 리포지토리 인터페이스
     * ─────────────────────────────────────────────────────
     */
    List<Trade> findAutoConfirmTargets(@Param("status") TradeStatus status,
                                       @Param("dueDate")LocalDateTime dueDate);
    boolean existsByPost_PostId(Long postId);

    List<Trade> findAllByBuyer_MemberIdOrderByTradeIdDesc(Long buyerId);

    List<Trade> findAllBySeller_MemberIdOrderByTradeIdDesc(Long sellerId);

    // status 필터링 쿼리 — readBuyerTrades / readSellerTrades 에서 메모리 필터 대신 DB 레벨 필터에 사용
    List<Trade> findAllByBuyer_MemberIdAndStatusOrderByTradeIdDesc(Long buyerId, TradeStatus status);

    List<Trade> findAllBySeller_MemberIdAndStatusOrderByTradeIdDesc(Long sellerId, TradeStatus status);

    @EntityGraph(attributePaths = {"post", "buyer", "seller"})
    List<Trade> findTop5ByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = {"post", "buyer", "seller"})
    Optional<Trade> findWithPostAndBuyerAndSellerByTradeId(Long tradeId);

    @EntityGraph(attributePaths = {"post", "buyer", "seller"})
    List<Trade> findAllByStatusOrderByCreatedAtDesc(TradeStatus status);

    @EntityGraph(attributePaths = {"post", "buyer", "seller"})
    List<Trade> findAllBySeller_MemberIdOrBuyer_MemberIdOrderByTradeIdDesc(Long sellerId, Long buyerId);

    @EntityGraph(attributePaths = {"post", "buyer", "seller"})
    @Query("""
            SELECT t
            FROM Trade t
            WHERE t.seller.memberId = :memberId
               OR t.buyer.memberId = :memberId
            ORDER BY t.createdAt DESC, t.tradeId DESC
            """)
    List<Trade> findRecentTradesForAdminMember(@Param("memberId") Long memberId);

    @Query("""
            SELECT t.tradeId
            FROM Trade t
            WHERE t.post.postId = :postId
            """)
    Optional<Long> findTradeIdByPostId(@Param("postId") Long postId);

    @Query("""
            SELECT COALESCE(SUM(t.tradePrice), 0)
            FROM Trade t
            WHERE t.createdAt >= :startInclusive
              AND t.createdAt < :endExclusive
            """)
    Long sumTradePriceByCreatedAtBetween(@Param("startInclusive") LocalDateTime startInclusive,
                                         @Param("endExclusive") LocalDateTime endExclusive);
}
