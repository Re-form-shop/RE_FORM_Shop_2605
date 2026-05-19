package com.re_form_shop_2605.entity.community;

import com.re_form_shop_2605.entity.BaseEntity;
import com.re_form_shop_2605.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-07
 * 설명: Reply의 Entity와 비즈니스 메서드
 * ─────────────────────────────────────────────────────
 */
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
    private Long replyId; // 댓글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPost communityPost; // 커뮤니티 게시글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // 작성자 member_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Reply reply; // 대댓글의 부모 댓글

    @Column(columnDefinition = "TEXT")
    private String replyContent; // 댓글 내용

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = false)
    private Boolean isDeleted = false; // 삭제 여부 (soft delete)

    @Column(columnDefinition = "INT DEFAULT 0", nullable = false)
    private int likeCount = 0; // 좋아요 수

    @Builder.Default
    @OneToMany(mappedBy = "reply")
    private List<Reply> replies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "reply")
    private List<ReplyLike> repliesLike = new ArrayList<>();

    // 댓글 삭제 (soft delete)
    public void markAsDeleted() {
        this.isDeleted = true;
    }

    // 댓글 좋아요 +1
    public void addLike() {
        this.likeCount++;
    }

    // 댓글 좋아요 -1
    public void removeLike() {
        if (this.likeCount > 0) this.likeCount--;
    }

    // 댓글 수정
    public void changeReply(String replyContent) {
        if (replyContent != null) this.replyContent = replyContent;
    }
}
