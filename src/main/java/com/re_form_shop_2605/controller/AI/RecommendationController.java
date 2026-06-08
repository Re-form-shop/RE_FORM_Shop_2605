package com.re_form_shop_2605.controller.AI;

import com.re_form_shop_2605.dto.AI.RecommendPostCardDTO;
import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.service.AI.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-06-08
 * 설명: AI 개인화 추천 API
 *      GET /api/recommendations
 * ─────────────────────────────────────────────────────
 */
@RestController
@Tag(name = "AI 추천 API", description = "관심 종목·키워드 + 최근 활동 기반 맞춤 게시글 추천")
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     *  GET /api/recommendations?size=10
     *
     * @param principal: 로그인 회원 (비로그인 시 빈 배열 반환)
     * @param size: 반환할 게시글 수 (기본 10, 최대 50)
     */
    @Operation(
            summary = "AI 개인화 추천",
            description = "관심 종목/키워드 + 최근 검색·클릭 이력을 종합해 맞춤 유니폼 게시글을 추천합니다. "
                    + "로그인 필수. 비로그인 시 빈 배열 반환."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<RecommendPostCardDTO>>> getRecommendations(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (principal == null) {
            return ResponseEntity.ok(ApiResponse.ok(List.of(), "로그인 후 이용 가능합니다."));
        }

        int safeSize = Math.min(Math.max(size, 1), 50);
        List<RecommendPostCardDTO> result =
                recommendationService.recommend(principal.getMemberId(), safeSize);

        return ResponseEntity.ok(ApiResponse.ok(result, "추천 게시글 조회 완료"));
    }
}
