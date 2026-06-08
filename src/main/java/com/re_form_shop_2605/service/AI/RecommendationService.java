package com.re_form_shop_2605.service.AI;

import com.re_form_shop_2605.dto.AI.RecommendPostCardDTO;
import com.re_form_shop_2605.entity.AI.UserSearchLog;
import com.re_form_shop_2605.entity.AI.UserViewLog;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.member.InterestKeyword;
import com.re_form_shop_2605.entity.member.InterestSetting;
import com.re_form_shop_2605.entity.trade.PostImage;
import com.re_form_shop_2605.repository.AI.UserSearchLogRepository;
import com.re_form_shop_2605.repository.AI.UserViewLogRepository;
import com.re_form_shop_2605.repository.member.InterestKeywordRepository;
import com.re_form_shop_2605.repository.member.InterestSettingRepository;
import com.re_form_shop_2605.repository.trade.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-08
 * 설명: AI 개인화 추천 서비스
 *
 * [선호도 텍스트 구성 — 우선순위 & 가중치]
 *   1. interest_setting (sport, team)         → 기본 2회 반복
 *   2. interest_keyword                       → 기본 1회 반복
 *   3. 최근 조회 이력 (user_view_log)           → 최근성 × 빈도 가중치 반복
 *   4. 최근 검색어   (user_search_log)          → 최근성 × 빈도 가중치 반복
 *
 * [이미 본 게시글 제외]
 *   user_view_log에 저장된 postId Set으로 PGVector 결과 필터링
 * ─────────────────────────────────────────────────────
 */
@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendationService {

    private final VectorStore vectorStore;
    private final InterestSettingRepository interestSettingRepository;
    private final InterestKeywordRepository interestKeywordRepository;
    private final UserViewLogRepository userViewLogRepository;
    private final UserSearchLogRepository userSearchLogRepository;
    private final PostRepository postRepository;

    // ─────────────────────────────────────────────────────
    // Public API
    // ─────────────────────────────────────────────────────

    /**
     * @param memberId: 로그인 회원 ID
     * @param size: 반환할 추천 게시글 수
     */
    public List<RecommendPostCardDTO> recommend(Long memberId, int size){

        // 1) 선호도 텍스트 생성
        String preferenceText = buildPreferenceText(memberId);

        if (preferenceText.isBlank()) {
            log.info("[Recommend] 선호도 정보 없음 — memberId={}", memberId);
            return List.of();
        }

        log.info("[Recommend] 선호도 텍스트 — memberId={}, text={}", memberId, preferenceText);

        // 2) 이미 본 postId Set 수집 (제외 필터용)
        Set<Long> viewedPostIds = new HashSet<>(
                userViewLogRepository.findViewedPostIdsByMemberId(memberId)
        );

        // 3) PGVector 유사도 검색 — 제외 후에도 size개가 남도록 여유분 확보
        int topK = size + viewedPostIds.size() + 20;
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(preferenceText)
                        .topK(topK)
                        .similarityThreshold(0.65) // 필요 시 0.55~0.70 사이에서 조정
                        .build()
        );

        // 4) 이미 본 게시글 제외 + 중복 제거 + size 개수 제한
        List<Long> recommendedIds = docs.stream()
                .filter(doc -> doc.getMetadata().containsKey("postId"))
                .map(doc -> Long.parseLong(doc.getMetadata().get("postId").toString()))
                .filter(postId -> !viewedPostIds.contains(postId)) // 핵심: 이미 본 것 제외
                .distinct()
                .limit(size)
                .toList();

        log.info("[Recommend] 최종 추천 {}개 — memberId={}, ids={}", recommendedIds.size(), memberId, recommendedIds);

        // 5) Post 상세 조회 -> DTO 변환
        return buildDTOs(recommendedIds, memberId);
    }

    // ─────────────────────────────────────────────────────
    // 선호도 텍스트 빌더
    // ─────────────────────────────────────────────────────

    private String buildPreferenceText(Long memberId) {
        StringBuilder sb = new StringBuilder();

        // 1) interest_setting (sport, team) — 기본 가중치 2회 반복
        InterestSetting setting = interestSettingRepository.findById(memberId).orElse(null);
        if (setting != null) {
            for (int i = 0; i < 2; i++) {
                sb.append(setting.getSport().name()).append(" ");
                if (setting.getTeam() != null) sb.append(setting.getTeam()).append(" ");
            }
        }

        // 2) interest_keyword — 1회 반복
        List<InterestKeyword> keywords =
                interestKeywordRepository.findAllByMember_MemberId(memberId);
        keywords.forEach(ik -> sb.append(ik.getKeyword()).append(" "));

        // 3) 조회 이력 — 최근성 × 빈도 가중치
        List<UserViewLog> viewLogs =
                userViewLogRepository.findTop100ByMemberIdOrderByViewedAtDesc(memberId);
        applyViewWeight(sb, viewLogs);

        // 4) 검색 이력 — 최근성 × 빈도 가중치
        List<UserSearchLog> searchLogs =
                userSearchLogRepository.findTop50ByMemberIdOrderBySearchedAtDesc(memberId);
        applySearchWeight(sb, searchLogs);

        return sb.toString().trim();
    }

    /**
     * 조회 이력에 최근성 × 빈도 가중치 적용
     *
     * 빈도 반영: 같은 sport/team을 자주 볼수록 텍스트에 더 많이 등장
     * 최근성 반영: 최근 3일 = 4배, 7일 = 3배, 30일 = 2배, 이후 = 1배
     * 총 가중치 = min(빈도 가중치 + 최근성 가중치, 5)로 cap
     */
    private void applyViewWeight(StringBuilder sb, List<UserViewLog> viewLogs) {
        // sport별 빈도 집계
        Map<String, Long> sportFreq = viewLogs.stream()
                .filter(v -> v.getSport() != null)
                .collect(Collectors.groupingBy(v -> v.getSport().name(), Collectors.counting()));

        // team별 빈도 집계
        Map<String, Long> teamFreq = viewLogs.stream()
                .filter(v -> v.getTeam() != null)
                .collect(Collectors.groupingBy(UserViewLog::getTeam, Collectors.counting()));

        for (UserViewLog vl : viewLogs) {
            int recency = recencyWeight(vl.getViewedAt());

            if (vl.getSport() != null) {
                long freq = sportFreq.getOrDefault(vl.getSport().name(), 1L);
                int weight = cap(recency + (int)(freq / 3)); // 3번 볼 때마다 +1
                appendRepeat(sb, vl.getSport().name(), weight);
            }
            if (vl.getTeam() != null) {
                long freq = teamFreq.getOrDefault(vl.getTeam(), 1L);
                int weight = cap(recency + (int)(freq / 3));
                appendRepeat(sb, vl.getTeam(), weight);
            }
            if (vl.getUniformName() != null) {
                appendRepeat(sb, vl.getUniformName(), recency);
            }
        }
    }

    /**
     * 검색 이력에 최근성 × 빈도 가중치 적용
     *
     * 같은 키워드를 자주 검색할수록 + 최근에 검색할수록 가중치 상승
     */
    private void applySearchWeight(StringBuilder sb, List<UserSearchLog> searchLogs) {
        // 키워드 빈도 집계
        Map<String, Long> keywordFreq = searchLogs.stream()
                .collect(Collectors.groupingBy(UserSearchLog::getKeyword, Collectors.counting()));

        // 같은 키워드가 여러 번 나올 경우 중복 반복 방지를 위해 처음 등장만 처리
        Set<String> processed = new HashSet<>();
        for (UserSearchLog sl : searchLogs) {
            if (processed.contains(sl.getKeyword())) continue;
            processed.add(sl.getKeyword());

            int recency = recencyWeight(sl.getSearchedAt());
            long freq   = keywordFreq.getOrDefault(sl.getKeyword(), 1L);
            int weight  = cap(recency + (int)(freq / 2)); // 2번 검색할 때마다 +1

            appendRepeat(sb, sl.getKeyword(), weight);
        }
    }

    // ─────────────────────────────────────────────────────
    // 공통 유틸
    // ─────────────────────────────────────────────────────

    /**
     * 최근성 가중치
     * 3일 이내 = 4, 7일 이내 = 3, 30일 이내 = 2, 이후 = 1
     */
    private int recencyWeight(LocalDateTime dateTime) {
        long daysAgo = ChronoUnit.DAYS.between(dateTime, LocalDateTime.now());
        if (daysAgo <= 3)  return 4;
        if (daysAgo <= 7)  return 3;
        if (daysAgo <= 30) return 2;
        return 1;
    }

    /** 가중치 최댓값 5로 cap */
    private int cap(int weight) {
        return Math.min(weight, 5);
    }

    /** 텍스트를 weight번 반복해서 StringBuilder에 추가 */
    private void appendRepeat(StringBuilder sb, String text, int weight) {
        for (int i = 0; i < weight; i++) {
            sb.append(text).append(" ");
        }
    }

    // ─────────────────────────────────────────────────────
    // 추천 이유 결정
    // ─────────────────────────────────────────────────────

    private String buildRecommendReason(Long memberId) {
        long viewCount   = userViewLogRepository.findTop100ByMemberIdOrderByViewedAtDesc(memberId).size();
        long searchCount = userSearchLogRepository.findTop50ByMemberIdOrderBySearchedAtDesc(memberId).size();
        return (viewCount + searchCount >= 5) ? "최근 활동 기반 추천" : "관심 종목 기반 추천";
    }

    // ─────────────────────────────────────────────────────
    // DTO 변환
    // ─────────────────────────────────────────────────────

    private List<RecommendPostCardDTO> buildDTOs(List<Long> postIds, Long memberId) {
        String reason = buildRecommendReason(memberId);
        List<RecommendPostCardDTO> result = new ArrayList<>();

        for (Long postId : postIds) {
            postRepository.findById(postId).ifPresent(post -> {

                // 삭제 / 숨김 게시글 제외
                if (post.getStatus() == PostStatus.DELETED ||
                        post.getStatus() == PostStatus.HIDDEN) return;

                // 썸네일: sortOrder 기준 첫 번째 이미지
                String thumbnail = post.getImages().stream()
                        .sorted(Comparator.comparingInt(PostImage::getSortOrder))
                        .map(PostImage::getImageUrl)
                        .findFirst()
                        .orElse(null);

                result.add(new RecommendPostCardDTO(
                        post.getPostId(),
                        post.getTitle(),
                        post.getTeam(),
                        post.getSport(),
                        post.getPrice(),
                        post.getGrade(),
                        post.getSize(),
                        post.getDeliveryType(),
                        post.getStatus(),
                        post.getViewCount(),
                        post.getWishCount(),
                        thumbnail,
                        post.getCreatedAt(),
                        reason
                ));
            });
        }
        return result;
    }
}
