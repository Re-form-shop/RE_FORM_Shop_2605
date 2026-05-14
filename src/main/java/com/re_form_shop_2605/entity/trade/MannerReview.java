package com.re_form_shop_2605.entity.trade;

import com.re_form_shop_2605.entity.BaseEntity;
import com.re_form_shop_2605.entity.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "manner_review",uniqueConstraints = {
        @UniqueConstraint(name = "uk_manner_review_trade_buyer", columnNames = {"trade_id", "buyer_id"})
})
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-07
 * 설명: 매너 리뷰 JPA 엔티티
 * ─────────────────────────────────────────────────────
 */
public class MannerReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manner_id", nullable = false)
    private Long mannerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "trade_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_manner_review_trade")
    )
    private Trade trade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "buyer_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_manner_review_buyer_member")
    )
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "seller_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_manner_review_seller_member")
    )
    private Member seller;

    @Column(name = "score", nullable = false)
    @Min(1) @Max(5)
    private double score;

    @Lob
    @Column(name = "content")
    private String content;

}
