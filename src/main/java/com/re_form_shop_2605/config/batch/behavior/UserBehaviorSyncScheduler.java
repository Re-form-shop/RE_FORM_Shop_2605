package com.re_form_shop_2605.config.batch.behavior;

import com.re_form_shop_2605.dto.AI.UserSearchRedisDTO;
import com.re_form_shop_2605.dto.AI.UserViewRedisDTO;
import com.re_form_shop_2605.entity.AI.UserSearchLog;
import com.re_form_shop_2605.entity.AI.UserViewLog;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.repository.AI.UserSearchLogRepository;
import com.re_form_shop_2605.repository.AI.UserViewLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-08
 * 설명: 10분마다 Redis에 쌓인 행동 이력을 MariaDB로 이관
 * ─────────────────────────────────────────────────────
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class UserBehaviorSyncScheduler {
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserViewLogRepository viewLogRepository;
    private final UserSearchLogRepository searchLogRepository;

    private static final String VIEW_KEY_PREFIX   = "behavior:view:";
    private static final String SEARCH_KEY_PREFIX = "behavior:search:";
    private static final String ACTIVE_MEMBERS    = "behavior:active:members";

    /**
     * 10분마다 실행
     * 처리 순서
     * 1. behavior:active:members Set에서 처리 대기 memberId 목록 조회
     * 2. 각 memberId 별로 view/search 이력을 MariaDB에 저장
     * 3. 처리 완료된 Redis 데이터 삭제
     */
    @Scheduled(fixedRate = 600_000) // 10분 = 600,000ms
    public void syncBehaviorToDb() {
        Set<Object> memberIds = redisTemplate.opsForSet().members(ACTIVE_MEMBERS);
        if (memberIds == null || memberIds.isEmpty()) return;

        log.info("[BehaviorSync] 처리 대상 멤버 수: {}", memberIds.size());

        for (Object memberIdObj : memberIds) {
            try {
                Long memberId = Long.parseLong(memberIdObj.toString());
                syncViewLogs(memberId);
                syncSearchLogs(memberId);

                // 처리 완료 -> active set에서 제거
                redisTemplate.opsForSet().remove(ACTIVE_MEMBERS, memberIdObj);
                log.debug("[BehaviorSync] memberId={} 동기화 완료", memberId);

            } catch (Exception e) {
                log.error("[BehaviorSync] memberId={} 동기화 실패 — {}", memberIdObj, e.getMessage());
                // 실패 시 해당 멤버는 다음 배치에서 재처리
            }
        }
    }

    /* 조회 이력 MariaDB 저장 */
    private void syncViewLogs(Long memberId) {
        String key = VIEW_KEY_PREFIX + memberId;
        List<Object> rawList = redisTemplate.opsForList().range(key, 0, -1);
        if (rawList == null || rawList.isEmpty()) return;

        List<UserViewLog> viewLogs = new ArrayList<>();
        for (Object raw : rawList) {
            try {
                // GenericJacksonJsonRedisSerializer가 타입 정보(@class)와 함께 직렬화하므로
                // 역직렬화 시 UserViewRedisDTO로 자동 변환됨
                UserViewRedisDTO dto = (UserViewRedisDTO) raw;

                Sport sport = null;
                if (dto.getSport() != null && !dto.getSport().isBlank()) {
                    try { sport = Sport.valueOf(dto.getSport()); }
                    catch (IllegalArgumentException ignored) {}
                }

                viewLogs.add(UserViewLog.builder()
                        .memberId(dto.getMemberId())
                        .postId(dto.getPostId())
                        .sport(sport)
                        .team(dto.getTeam())
                        .uniformName(dto.getUniformName())
                        .viewedAt(toLocalDateTime(dto.getViewedAt()))
                        .build());

            } catch (Exception e) {
                log.warn("[BehaviorSync] view 데이터 파싱 실패 — {}", e.getMessage());
            }
        }

        if (!viewLogs.isEmpty()) {
            viewLogRepository.saveAll(viewLogs);
            redisTemplate.delete(key); // 처리 완료 후 Redis 삭제
            log.debug("[BehaviorSync] view {}건 저장 — memberId={}", viewLogs.size(), memberId);
        }
    }

    /* 검색 이력 MariaDB에 저장 */
    private void syncSearchLogs(Long memberId) {
        String key = SEARCH_KEY_PREFIX + memberId;
        List<Object> rawList = redisTemplate.opsForList().range(key, 0, -1);
        if (rawList == null || rawList.isEmpty()) return;

        List<UserSearchLog> searchLogs = new ArrayList<>();
        for (Object raw : rawList) {
            try {
                UserSearchRedisDTO dto = (UserSearchRedisDTO) raw;
                searchLogs.add(UserSearchLog.builder()
                        .memberId(dto.getMemberId())
                        .keyword(dto.getKeyword())
                        .searchedAt(toLocalDateTime(dto.getSearchedAt()))
                        .build());

            } catch (Exception e) {
                log.warn("[BehaviorSync] search 데이터 파싱 실패 — {}", e.getMessage());
            }
        }

        if (!searchLogs.isEmpty()) {
            searchLogRepository.saveAll(searchLogs);
            redisTemplate.delete(key);
            log.debug("[BehaviorSync] search {}건 저장 — memberId={}", searchLogs.size(), memberId);
        }
    }

    /* epoch milliseconds -> LocalDateTime 변환 */
    private LocalDateTime toLocalDateTime(long epochMilli) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(epochMilli),
                ZoneId.systemDefault()
        );
    }
}
