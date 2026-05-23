package com.re_form_shop_2605.service.draft;

import com.re_form_shop_2605.dto.AI.RiskAnalysisResultDTO;
import com.re_form_shop_2605.dto.draft.PostDraftDTO;
import com.re_form_shop_2605.dto.draft.PostDraftStateDTO;
import com.re_form_shop_2605.dto.draft.ReplyDraftDTO;
import com.re_form_shop_2605.entity.Enum.TargetType;
import com.re_form_shop_2605.service.AI.ModerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 게시글과 댓글 작성 시 임시 저장 기능을 제공하는 서비스 클래스
 * ─────────────────────────────────────────────────────
 */
// 게시글/댓글 작성 임시 저장
@Log4j2
@RequiredArgsConstructor
@Service
public class DraftService {
    private static final String POST_PREFIX = "draft:post:";
    private static final String REPLY_PREFIX = "draft:reply:";
    private static final Duration TTL = Duration.ofDays(7);

    // Redis 임시 저장용 템플릿
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final ModerationService moderationService;

    // 회원별 게시글 초안을 저장
    public PostDraftStateDTO savePostDraft(Long memberId, PostDraftDTO draftDTO) {
        PostDraftDTO mergedDraft = sanitizePostDraft(mergePostDraft(getExistingDraft(memberId), draftDTO));
        RiskAnalysisResultDTO moderation = toFlaggedModeration(moderationService.checkDraft(
                buildPostDraftModerationContent(mergedDraft),
                TargetType.POST
        ));
        PostDraftStateDTO state = new PostDraftStateDTO(mergedDraft, moderation);
        redisTemplate.opsForValue().set(buildPostKey(memberId), state, TTL);
        return state;
    }

    // 회원별 게시글 초안을 조회
    public PostDraftStateDTO getPostDraft(Long memberId) {
        Object value = redisTemplate.opsForValue().get(buildPostKey(memberId));
        return convertToPostDraftState(memberId, value);
    }

    private PostDraftDTO getExistingDraft(Long memberId) {
        PostDraftStateDTO existingState = convertToPostDraftState(
                memberId,
                redisTemplate.opsForValue().get(buildPostKey(memberId))
        );
        return existingState == null ? null : existingState.draft();
    }

    private PostDraftStateDTO convertToPostDraftState(Long memberId, Object value) {
        if (value == null) {
            return null;
        }

        try {
            PostDraftStateDTO state = objectMapper.convertValue(value, PostDraftStateDTO.class);
            if (state != null && state.draft() != null) {
                return new PostDraftStateDTO(sanitizePostDraft(state.draft()), state.moderation());
            }
        } catch (IllegalArgumentException e) {
            log.info("기존 형식 게시글 초안 감지. legacy draft 로 fallback 합니다. memberId={}", memberId);
        }

        PostDraftDTO legacyDraft = objectMapper.convertValue(value, PostDraftDTO.class);
        if (legacyDraft == null) {
            return null;
        }
        return new PostDraftStateDTO(sanitizePostDraft(legacyDraft), null);
    }

    // 회원별 게시글 초안을 삭제
    public void removePostDraft(Long memberId) {
        redisTemplate.delete(buildPostKey(memberId));
    }

    // 회원별 댓글 초안을 대상 기준으로 저장
    public void saveReplyDraft(Long memberId, ReplyDraftDTO draftDTO) {
        redisTemplate.opsForValue().set(buildReplyKey(memberId, draftDTO.targetType(), draftDTO.targetId()), draftDTO, TTL);
    }

    // 회원별 댓글 초안을 대상 기준으로 조회
    public ReplyDraftDTO getReplyDraft(Long memberId, String targetType, Long targetId) {
        Object value = redisTemplate.opsForValue().get(buildReplyKey(memberId, targetType, targetId));
        if (value == null) {
            return null;
        }

        return objectMapper.convertValue(value, ReplyDraftDTO.class);
    }

    // 회원별 댓글 초안을 대상 기준으로 삭제
    public void removeReplyDraft(Long memberId, String targetType, Long targetId) {
        redisTemplate.delete(buildReplyKey(memberId, targetType, targetId));
    }

    // 게시글 초안 저장 키를 생성
    private String buildPostKey(Long memberId) {
        return POST_PREFIX + memberId;
    }

    // 댓글 초안 저장 키를 생성
    private String buildReplyKey(Long memberId, String targetType, Long targetId) {
        return REPLY_PREFIX + memberId + ":" + targetType + ":" + targetId;
    }

    private PostDraftDTO mergePostDraft(PostDraftDTO existingDraft, PostDraftDTO incomingDraft) {
        if (existingDraft == null) {
            return incomingDraft;
        }
        if (incomingDraft == null) {
            return existingDraft;
        }

        return new PostDraftDTO(
                firstNonNull(incomingDraft.title(), existingDraft.title()),
                firstNonNull(incomingDraft.content(), existingDraft.content()),
                firstNonNull(incomingDraft.sport(), existingDraft.sport()),
                firstNonNull(incomingDraft.team(), existingDraft.team()),
                firstNonNull(incomingDraft.uniformNumber(), existingDraft.uniformNumber()),
                firstNonNull(incomingDraft.condition(), existingDraft.condition()),
                firstNonNull(incomingDraft.size(), existingDraft.size()),
                firstNonNull(incomingDraft.tradeType(), existingDraft.tradeType()),
                firstNonNull(incomingDraft.price(), existingDraft.price()),
                firstNonNull(incomingDraft.directTradeLocation(), existingDraft.directTradeLocation()),
                firstNonNull(incomingDraft.imageUrls(), existingDraft.imageUrls())
        );
    }

    private PostDraftDTO sanitizePostDraft(PostDraftDTO draftDTO) {
        if (draftDTO == null) {
            return null;
        }

        return new PostDraftDTO(
                draftDTO.title(),
                draftDTO.content(),
                draftDTO.sport(),
                draftDTO.team(),
                draftDTO.uniformNumber(),
                draftDTO.condition(),
                draftDTO.size(),
                draftDTO.tradeType(),
                draftDTO.price(),
                draftDTO.directTradeLocation(),
                null
        );
    }

    private String buildPostDraftModerationContent(PostDraftDTO draftDTO) {
        String title = draftDTO.title() == null ? "" : draftDTO.title().trim();
        String content = draftDTO.content() == null ? "" : draftDTO.content().trim();
        return (title + " " + content).trim();
    }

    private RiskAnalysisResultDTO toFlaggedModeration(RiskAnalysisResultDTO moderation) {
        return moderation != null && moderation.riskLevel() != null ? moderation : null;
    }

    private <T> T firstNonNull(T preferred, T fallback) {
        return preferred != null ? preferred : fallback;
    }
}
