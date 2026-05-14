package com.re_form_shop_2605.entity.trade;

import com.re_form_shop_2605.entity.BaseEntity;
import com.re_form_shop_2605.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wish",uniqueConstraints = {
        @UniqueConstraint(name = "uk_wish_member_post", columnNames = {"member_id", "post_id"})
})
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-07
 * 설명: 찜 정보 JPA 엔티티
 * ─────────────────────────────────────────────────────
 */
public class Wish extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id", nullable = false)
    private Long wishId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_wish_member")
    )
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "post_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_wish_post")
    )
    private Post post;

}
