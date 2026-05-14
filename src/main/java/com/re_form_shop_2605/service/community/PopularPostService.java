package com.re_form_shop_2605.service.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.re_form_shop_2605.dto.batch.PopularPostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-13
 * 설명: 커뮤니티 인기글 조회 서비스
 *       - Redis ZSet에서 score 높은 순으로 인기글 조회
 *       - Redis 만료 시 DB에서 직접 조회 (fallback)
 * ─────────────────────────────────────────────────────
 */

@Log4j2
@Service
@RequiredArgsConstructor
public class PopularPostService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    /* Batch에서 저장한 Redis ZSet에서 인기글 조회 */
    public List<PopularPostDTO> getPopularPosts(int size) {
        // 1) reverseRange()로 score 높은 순 상위 size개 조회
        // "popular:posts"에서 score가 높은 순으로 0번째~size-1번째까지
        Set<String> stringSet = redisTemplate.opsForZSet().reverseRange("popular:posts", 0, size - 1);

        // 2) DTO 변환
        return stringSet.stream()
                .map(string -> {
                    try {
                        return objectMapper.readValue(string, PopularPostDTO.class);
                    } catch (Exception e) {
                        log.error("getPopularPosts : 인기글 JSON 변환 실패 | {}", e.getMessage());
                        return null;
                    }
                })
                .filter(dto -> dto != null)
                .toList();
    }
}