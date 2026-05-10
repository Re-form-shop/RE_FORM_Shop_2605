package com.re_form_shop_2605.entity.community;

import com.re_form_shop_2605.entity.BaseEntity;
import com.re_form_shop_2605.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id", nullable = false)
    private Long replyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPost postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Reply parentId; // 대댓글의 부모 댓글

    @Column(columnDefinition = "TEXT")
    private String replyContent; // 댓글 내용

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private Boolean isDeleted = false;

    @Column(columnDefinition = "INT DEFAULT 0", nullable = false)
    private int likeCount = 0;
}
