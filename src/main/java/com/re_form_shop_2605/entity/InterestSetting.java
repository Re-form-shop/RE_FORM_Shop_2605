package com.re_form_shop_2605.entity;

import com.re_form_shop_2605.entity.Enum.SportType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Interest_setting")
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
    private SportType sport; // 관심 종목 (야구/농구/배구/이스포츠)

    @Column(name = "team", length = 100)
    private String team; // 관심 구단

}
