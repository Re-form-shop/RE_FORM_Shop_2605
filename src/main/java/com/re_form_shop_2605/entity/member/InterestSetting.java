package com.re_form_shop_2605.entity.member;

import com.re_form_shop_2605.entity.Enum.Sport;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "interest_setting")
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-07
 * 설명: 관심 종목 JPA 엔티티
 * ─────────────────────────────────────────────────────
 */
public class InterestSetting {
    @Id
    @Column(name = "member_id", nullable = false)
    private Long memberId; // 회원 id

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_interest_setting_member")
    )
    private Member member; // // 회원 id

    @Enumerated(EnumType.STRING)
    @Column(name = "sport", nullable = false)
    private Sport sport; // 관심 종목 (야구/농구/배구/이스포츠)

    @Column(name = "team", length = 100)
    private String team; // 관심 구단

}
