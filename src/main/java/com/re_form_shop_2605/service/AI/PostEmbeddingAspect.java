package com.re_form_shop_2605.service.AI;

import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.repository.trade.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-08
 * 설명: Post 등록/수정 시 PGVector에 자동으로 임베딩 저장
 * ─────────────────────────────────────────────────────
 */
@Log4j2
@Aspect
@Component
@RequiredArgsConstructor
public class PostEmbeddingAspect {
    private final VectorStore vectorStore;
    private final PostRepository postRepository;

    /**
     * PostService.addPost(Long sellerId, PostRequestDTO, List<String>) 반환값 = Long postId
     */
    @AfterReturning(
            pointcut = "execution(* com.re_form_shop_2605.service.trade.PostService.addPost(..))",
            returning = "result"
    )
    public void embedOnCreate(Object result) {
        try {
            Long postId = (Long) result;
            embedPost(postId);
            log.debug("[PostEmbedding] 신규 임베딩 완료 — postId={}", postId);
        } catch (Exception e) {
            log.warn("[PostEmbedding] 신규 임베딩 실패 — {}", e.getMessage());
        }
    }

    /**
     * PostService.modifyPost(Long postId, Long sellerId, PostUpdateRequestDTO, List<String>)
     * args[0] = postId
     */
    @AfterReturning(
            "execution(* com.re_form_shop_2605.service.trade.PostService.modifyPost(..))"
    )
    public void embedOnUpdate(JoinPoint joinPoint) {
        try {
            Long postId = (Long) joinPoint.getArgs()[0];
            vectorStore.delete(List.of("postId-" + postId)); // 기존 임베딩 삭제
            embedPost(postId);
            log.debug("[PostEmbedding] 임베딩 갱신 완료 — postId={}", postId);
        } catch (Exception e) {
            log.warn("[PostEmbedding] 임베딩 갱신 실패 — {}", e.getMessage());
        }
    }

    /**
     * Post → 임베딩 텍스트 생성 후 VectorStore에 저장
     */
    public void embedPost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) return;

        // 임베딩 텍스트: 의미가 풍부할수록 추천 정확도 상승
        String content = String.join(" ",
                safeStr(post.getSport() != null ? post.getSport().name() : null),
                safeStr(post.getTeam()),
                safeStr(post.getUniformName()),
                safeStr(post.getTitle()),
                // 본문 앞 300자 (PostSearchService와 동일 기준)
                post.getContent() != null
                        ? post.getContent().substring(0, Math.min(post.getContent().length(), 300))
                        : ""
        ).trim();

        Document document = new Document(
                "postId-" + postId,  // 고유 ID — 수정 시 삭제 기준
                content,
                Map.of(
                        "postId", postId.toString(),
                        "sport",  safeStr(post.getSport() != null ? post.getSport().name() : null),
                        "team",   safeStr(post.getTeam())
                )
        );

        vectorStore.add(List.of(document));
    }

    private String safeStr(String value) {
        return value != null ? value : "";
    }
}
