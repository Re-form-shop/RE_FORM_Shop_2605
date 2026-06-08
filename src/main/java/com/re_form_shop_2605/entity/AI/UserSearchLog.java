package com.re_form_shop_2605.entity.AI;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-05
 * 설명: 검색 키워드 이력 Entity
 * ─────────────────────────────────────────────────────
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "user_search_log",
        indexes = {
                @Index(name = "idx_search_log_member_searched", columnList = "member_id, search_at DESC")
        }
)
public class UserSearchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "keyword", nullable = false, length = 200)
    private String keyword;

    @Column(name = "searched_at", nullable = false)
    private LocalDateTime searchedAt;
}
