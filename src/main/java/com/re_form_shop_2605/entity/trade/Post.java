package com.re_form_shop_2605.entity.trade;

import com.re_form_shop_2605.entity.*;
import com.re_form_shop_2605.entity.Enum.*;
import com.re_form_shop_2605.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long postId; // 게시글 번호

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "seller_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_post_member")
    )
    private Member sellerId; // 판매자 member id

    @Column(name = "title", length = 200)
    private String title; // 제목

    @Lob
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content; // 본문

    @Enumerated(EnumType.STRING)
    @Column(name = "sport",nullable = false)
    private Sport sport; // 종목

    @Column(name = "team", nullable = false)
    private String team; // 구단명

    @Column(name = "uniform_name",length = 200, nullable = false)
    private String uniformName; // 유니폼명

    @Enumerated(EnumType.STRING)
    @Column(name = "grade",nullable = false)
    private Grade grade; // 유니폼 상태 등급

    @Column(name = "size",length = 10)
    private String size; // 유니폼 사이즈 (85 ~120)

    @Column(name = "marking",columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean marking; // 마킹 여부

    @Column(name = "price",nullable = false)
    private int price; // 희망 가격

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type",nullable = false)
    private DeliveryType deliveryType; // 수령 방법

    @Enumerated(EnumType.STRING)
    @Column(name = "status",columnDefinition = "", nullable = false)
    private PostStatus status;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    @Column(name = "wish_count", nullable = false)
    private Integer wishCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level")
    private RiskLevel riskLevel;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<PostImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Wish> wishes = new ArrayList<>();

    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY)
    private Trade trade;

    public void changePost(String title, String content, Grade grade, String size, Boolean marking, Integer price, DeliveryType deliveryType) {
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
        if (grade != null) {
            this.grade = grade;
        }
        if (size != null) {
            this.size = size;
        }
        if (marking != null) {
            this.marking = marking;
        }
        if (price != null) {
            this.price = price;
        }
        if (deliveryType != null) {
            this.deliveryType = deliveryType;
        }
    }

    public void markDeleted() {
        this.status = PostStatus.DELETED;
    }

}
