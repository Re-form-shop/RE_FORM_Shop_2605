package com.re_form_shop_2605.service.statistics;

import com.re_form_shop_2605.dto.statistics.StatisticsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-27
 * 설명: 메인 화면 통계 조회 서비스
 * ─────────────────────────────────────────────────────
 */

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final RedisTemplate<String, String> redisTemplate;

    public StatisticsResponseDTO getStatistics() {
        String tradeCount = redisTemplate.opsForValue().get("stats:trade_count");
        String productCount = redisTemplate.opsForValue().get("stats:product_count");
        String memberCount = redisTemplate.opsForValue().get("stats:member_count");
        String satisfaction = redisTemplate.opsForValue().get("stats:satisfaction");

        return new StatisticsResponseDTO(
                tradeCount != null ? Long.parseLong(tradeCount) : 0,
                productCount != null ? Long.parseLong(productCount) : 0,
                memberCount != null ? Long.parseLong(memberCount) : 0,
                satisfaction != null ? Long.parseLong(satisfaction) : 0
        );
    }
}