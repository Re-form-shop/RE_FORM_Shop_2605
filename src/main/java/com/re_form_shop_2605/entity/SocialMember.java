package com.re_form_shop_2605.entity;

import com.re_form_shop_2605.entity.Enum.Provider;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "social_member", uniqueConstraints = {
                @UniqueConstraint(name = "uk_social_provider_user", columnNames = {"provider", "provider_id"}),
                @UniqueConstraint(name = "uk_social_member_provider", columnNames = {"member_id", "provider"})
})
public class SocialMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_id")
    private int socialId; // 소셜 id

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "member_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_social_member_member")
    )
    private Member member; // 회원 id

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider; // 소셜 로그인 경로 (카카오/구글)

    @Column(name = "provider_id", nullable = false, length = 100)
    private String providerId; // 소셜 제공자 id
}
