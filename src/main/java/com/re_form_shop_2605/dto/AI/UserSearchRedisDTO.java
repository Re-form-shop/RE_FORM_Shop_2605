package com.re_form_shop_2605.dto.AI;

import lombok.*;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-05
 * 설명: Redis 저장용 검색 이력 DTO
 * ─────────────────────────────────────────────────────
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSearchRedisDTO {
    private Long memberId;
    private String keyword;
    private long searchedAt; // epoch milliseconds
}
