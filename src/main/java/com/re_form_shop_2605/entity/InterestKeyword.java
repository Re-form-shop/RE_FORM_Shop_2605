package com.re_form_shop_2605.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Interest_keyword", uniqueConstraints = {
        @UniqueConstraint(name = "uk_interest_keyword_member_keyword", columnNames = {"member_id", "keyword"})
})
public class InterestKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_interest_keyword_member")
    )
    private Member member; // 회원 ID

    @Column(name = "keyword", nullable = false, length = 200)
    private String keyword; // 관심 키워드
}
