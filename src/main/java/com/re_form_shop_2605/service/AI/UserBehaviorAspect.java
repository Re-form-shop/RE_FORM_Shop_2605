package com.re_form_shop_2605.service.AI;

import com.re_form_shop_2605.dto.AI.UserSearchRedisDTO;
import com.re_form_shop_2605.dto.AI.UserViewRedisDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.repository.trade.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-08
 * 설명: 기존 PostController 수정 없이 AOP로 행동 이력을 Redis에 자동 기록
 * ─────────────────────────────────────────────────────
 */
@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class UserBehaviorAspect {
    private final RedisTemplate<String, Object> redisTemplate;
    private final PostRepository postRepository;

    // Redis 키 prefix
    private static final String VIEW_KEY_PREFIX = "behavior:view:";
    private static final String SEARCH_KEY_PREFIX = "behavior:search:";
    private static final String ACTIVE_MEMBERS = "behavior:active:members";

    // Redis 데이터 보관 기간 (30일)
    private static final long TTL_DAYS = 30;

    /* 1. 게시글 상세 클릭 기록 */
    @AfterReturning(
            "execution(* com.re_form_shop_2605.controller.trade.PostController.readListing(..))"
    )
    public void logPostView(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            Long postId = (Long) args[0];
            MemberSecurityDTO principal = (MemberSecurityDTO) args[1];

            if (principal == null) return; // 비로그인 무시

            Post post = postRepository.findById(postId).orElse(null);
            if (post == null) return;

            UserViewRedisDTO dto = UserViewRedisDTO.builder()
                    .memberId(principal.getMemberId())
                    .postId(postId)
                    .sport(post.getSport() != null ? post.getSport().name() : null)
                    .team(post.getTeam())
                    .uniformName(post.getUniformName())
                    .viewedAt(System.currentTimeMillis()) // epoch ms
                    .build();

            String key = VIEW_KEY_PREFIX + principal.getMemberId();
            redisTemplate.opsForList().rightPush(key, dto);
            redisTemplate.expire(key, TTL_DAYS, TimeUnit.DAYS);

            // 배치가 처리할 멤버로 등록
            redisTemplate.opsForSet().add(ACTIVE_MEMBERS, principal.getMemberId().toString());

            log.debug("[BehaviorAspect] 조회 기록 — memberId={}, postId={}", principal.getMemberId(), postId);

        } catch (Exception e) {
            // 추천 기능 오류가 메인 기능에 영향주지 않도록 예외 처리
            log.warn("[BehaviorAspect] 조회 기록 실패 — {}", e.getMessage());
        }
    }

    /* 2. 검색 키워드 기록 */
    @AfterReturning(
            "execution(* com.re_form_shop_2605.controller.trade.PostController.readListings(..))"
    )
    public void logSearch(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            MemberSecurityDTO principal = (MemberSecurityDTO) args[0];
            String keyword = (String) args[2]; // readListings 3번째 파라미터

            if (principal == null || keyword == null || keyword.isBlank()) return;

            UserSearchRedisDTO dto = UserSearchRedisDTO.builder()
                    .memberId(principal.getMemberId())
                    .keyword(keyword.trim())
                    .searchedAt(System.currentTimeMillis()) // epoch ms
                    .build();

            String key = SEARCH_KEY_PREFIX + principal.getMemberId();
            redisTemplate.opsForList().rightPush(key, dto);
            redisTemplate.expire(key, TTL_DAYS, TimeUnit.DAYS);

            redisTemplate.opsForSet().add(ACTIVE_MEMBERS, principal.getMemberId().toString());

            log.debug("[BehaviorAspect] 검색 기록 — memberId={}, keyword={}", principal.getMemberId(), keyword);

        } catch (Exception e) {
            log.warn("[BehaviorAspect] 검색 기록 실패 — {}", e.getMessage());
        }
    }
}