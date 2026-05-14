package com.re_form_shop_2605.service.draft;

import com.re_form_shop_2605.dto.draft.PostDraftDTO;
import com.re_form_shop_2605.dto.draft.ReplyDraftDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

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

    // 회원별 게시글 초안을 저장
    public void savePostDraft(Long memberId, PostDraftDTO draftDTO) {
        redisTemplate.opsForValue().set(buildPostKey(memberId), draftDTO, TTL);
    }

    // 회원별 게시글 초안을 조회
    public PostDraftDTO getPostDraft(Long memberId) {
        Object value = redisTemplate.opsForValue().get(buildPostKey(memberId));
        if (value == null) {
            return null;
        }

        return objectMapper.convertValue(value, PostDraftDTO.class);
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
}
