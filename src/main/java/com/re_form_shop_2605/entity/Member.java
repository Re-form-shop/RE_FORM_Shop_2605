package com.re_form_shop_2605.entity;

import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.Role;
import com.re_form_shop_2605.entity.Enum.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long member_id; // 회원 id

    @Column(name = "email", nullable = false, unique = true,  length = 100)
    private String email; // 회원 이메일

    @Column(name = "password", length = 255)
    private String password; // 회원 비밀번호

    @Column(name = "nickname", nullable = false, unique = true,  length = 50)
    private String nickname; // 회원 닉네임

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl; // 회원 프로필 이미지 url

    @Column(name = "bio", length = 200)
    private String bio; // 회원 자기소개

    @Column(name = "manner_score",  nullable = false, precision = 3, scale = 2)
    private BigDecimal mannerScore = BigDecimal.valueOf(0.00); // 매너 평균 점수

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role = Role.USER; // 사용자 분류(유저, 관리자)

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private MemberStatus status = MemberStatus.ACTIVE; // 활동상태 (활성화/정지/탈퇴)

    @Column(name = "warning_count" , nullable = false)
    private int warningCount = 0; // 누적 경고 횟수

    @Column(name = "email_event", nullable = false)
    private boolean emailEvent; // 이벤트 이메일 수신 여부

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<SocialMember> socialMembers = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<InterestKeyword> interestKeywords = new ArrayList<>();

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private InterestSetting interestSetting;

}

