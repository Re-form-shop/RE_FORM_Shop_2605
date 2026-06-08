package com.re_form_shop_2605.entity.AI;

import com.re_form_shop_2605.entity.Enum.Sport;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-05
 * 설명: 게시글 조회 이력 Entity
 * ─────────────────────────────────────────────────────
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "user_view_log",
        indexes = {
                @Index(name = "idx_view_log_member_viewed", columnList = "member_id, viewed_at DESC"),
                @Index(name = "idx_view_log_member_post",   columnList = "member_id, post_id")
        }
)
public class UserViewLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK 없이 Long으로 저장 - 배치 삽입 시 Member 조회 불필요
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    // Post 삭제 후에도 이력은 남아야 하므로 FK 없이 Long 저장
    @Column(name = "post_id", nullable = false)
    private Long postId;

    // 추천 텍스트 생성을 위한 비정규화 컬럼
    @Enumerated(EnumType.STRING)
    @Column(name = "sport", length = 20)
    private Sport sport;

    @Column(name = "team", length = 100)
    private String team;

    @Column(name = "uniform_name", length = 200)
    private String uniformName;

    @Column(name = "viewed_at", nullable = false)
    private LocalDateTime viewedAt;
}
