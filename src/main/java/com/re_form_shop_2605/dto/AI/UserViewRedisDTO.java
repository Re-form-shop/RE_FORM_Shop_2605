package com.re_form_shop_2605.dto.AI;

import com.re_form_shop_2605.entity.trade.Post;
import lombok.*;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-05
 * 설명: Redis 저장용 조회 이력 DTO
 * ─────────────────────────────────────────────────────
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserViewRedisDTO {
    private Long memberId;
    private Long postId;
    private String sport;
    private String team;
    private String uniformName;
    private long viewedAt; // epoch milliseconds
}
