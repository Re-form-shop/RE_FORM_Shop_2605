package com.re_form_shop_2605.entity.community;

import com.re_form_shop_2605.entity.BaseEntity;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.entity.Enum.CommunityPostStatus;
import com.re_form_shop_2605.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comm_id", nullable = false)
    private Long commId; // 커뮤니티 게시글 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport_category", nullable = false)
    private Sport sportCategory; // 종목

    @Column(name = "team_category", length = 50)
    private String teamCategory; // 구단명

    @Column(name = "comm_title", length = 200, nullable = false)
    private String commTitle ; // 제목

    @Column(name = "comm_content", columnDefinition = "TEXT")
    private String commContent; // 본문

    @Column(name = "comm_image_url", length = 500)
    private String commImageUrl; // 첨부 이미지 URL

    @Column(name = "comm_view_count", columnDefinition = "INT DEFAULT 0", nullable = false)
    private int commViewCount = 0; // 조회수

    @Column(name = "like_count", columnDefinition = "INT DEFAULT 0", nullable = false)
    private int likeCount = 0; // 좋아요 수

    @Column(name = "comment_count", columnDefinition = "INT DEFAULT 0", nullable = false)
    private int commentCount = 0; // 댓글 수

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CommunityPostStatus status; // 게시글 상태

    @OneToMany(mappedBy = "communityPost")
    private List<Reply> replies =  new ArrayList<>();
}
