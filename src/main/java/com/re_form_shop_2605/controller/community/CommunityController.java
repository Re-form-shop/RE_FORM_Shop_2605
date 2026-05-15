package com.re_form_shop_2605.controller.community;

import com.re_form_shop_2605.dto.batch.PopularPostDTO;
import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.community.*;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.service.community.CommunityService;
import com.re_form_shop_2605.service.community.PopularPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: 커뮤니티 Controller
 * ─────────────────────────────────────────────────────
 */
@RestController
@Tag(name = "커뮤니티 게시판 API", description = "커뮤니티 게시글 작성·수정·삭제·조회 및 인기글 조회 API")
@RequestMapping("/api/community")
@RequiredArgsConstructor
@Tag(name = "커뮤니티 API", description = "커뮤니티 게시글 관련 API")
public class CommunityController {
    private final CommunityService communityService;
    private final PopularPostService popularPostService;

    // GET /api/community
    @Operation(summary = "커뮤니티 게시글 목록 조회", description = "종목 필터와 페이지 정보를 기준으로 게시글 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CommunityPostListItemDTO>>> listPosts(
            @RequestParam(required = false) Sport sport,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(
                ApiResponse.ok(communityService.readPosts(sport, page, size), "게시글 목록 조회 완료")
        );
    }

    // GET /api/community/{commId}
    @Operation(summary = "커뮤니티 게시글 상세 조회", description = "게시글 ID로 상세 정보를 조회합니다. 비로그인 시 isLiked = false")
    @GetMapping("/{commId}")
    public ResponseEntity<ApiResponse<CommunityPostDetailDTO>> readPosts(
            @PathVariable Long commId,
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(communityService.readPost(commId, principal.getMemberId()), "게시글 상세 조회 완료")
        );
    }

    // POST /api/community
    @Operation(summary = "커뮤니티 게시글 작성", description = "새 게시글을 작성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<CommIdResponseDTO>> addPost(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody CommunityPostCreateRequestDTO requestDTO
    ) {
        Long commId = communityService.addPost(principal.getMemberId(), requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(new CommIdResponseDTO(commId), "게시글 작성 완료"));
    }

    // PUT /api/community/{commId}
    @Operation(summary = "커뮤니티 게시글 수정", description = "본인이 작성한 게시글을 수정합니다.")
    @PutMapping("/{commId}")
    public ResponseEntity<ApiResponse<CommIdResponseDTO>> modifyPost(
            @PathVariable Long commId,
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody CommunityPostUpdateRequestDTO requestDTO
    ) {
        communityService.modifyPost(commId, principal.getMemberId(), requestDTO);
        return ResponseEntity.ok(
                ApiResponse.ok(new CommIdResponseDTO(commId), "게시글 수정 완료")
        );
    }

    // DELETE /api/community/{commId}
    @Operation(summary = "커뮤니티 게시글 삭제", description = "본인이 작성한 게시글을 삭제합니다.")
    @DeleteMapping("/{commId}")
    public ResponseEntity<ApiResponse<Void>> removePost(
            @PathVariable Long commId,
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        communityService.removePost(commId, principal.getMemberId());
        return ResponseEntity.ok(ApiResponse.ok(null, "게시글 삭제 완료"));
    }

    // POST /api/community/{commId}/like
    @Operation(summary = "커뮤니티 게시글 좋아요 토글", description = "좋아요가 없으면 추가(+1), 있으면 취소(-1)합니다.")
    @PostMapping("/{commId}/like")
    public ResponseEntity<ApiResponse<Integer>> toggleLike(
            @PathVariable Long commId,
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(communityService.toggleLike(commId, principal.getMemberId()), "좋아요 처리 완료")
        );
    }

    // GET /api/community/{commId}/replies
    @Operation(summary = "댓글 목록 조회", description = "게시글의 댓글과 대댓글 목록을 조회합니다. 작성자는 익명 처리됩니다.")
    @GetMapping("/{commId}/replies")
    public ResponseEntity<ApiResponse<List<ReplyResponseDTO>>> readReplies(
            @PathVariable Long commId,
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(communityService.readReplies(commId, principal.getMemberId()), "댓글 목록 조회 완료")
        );
    }

    // POST /api/community/{commId}/replies
    @Operation(summary = "댓글 작성", description = "게시글에 댓글 또는 대댓글을 작성합니다. parentId = null이면 최상위 댓글, 값이 있으면 대댓글입니다.")
    @PostMapping("/{commId}/replies")
    public ResponseEntity<ApiResponse<ReplyResponseDTO>> addReply(
            @PathVariable Long commId,
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody ReplyCreateRequestDTO requestDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(
                        communityService.addReply(commId, principal.getMemberId(), requestDTO), "댓글 작성 완료"
                ));
    }

    // DELETE /api/community/replies/{replyId}
    @Operation(summary = "댓글 삭제", description = "본인이 작성한 댓글을 삭제합니다.")
    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<ApiResponse<Void>> removeReply(
            @PathVariable Long replyId,
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        communityService.removeReply(replyId, principal.getMemberId());
        return ResponseEntity.ok(ApiResponse.ok(null, "댓글 삭제 완료"));
    }

    // PUT /api/community/replies/{replyId}
    @Operation(summary = "댓글 수정", description = "본인이 작성한 댓글을 수정합니다.")
    @PutMapping("/replies/{replyId}")
    public ResponseEntity<ApiResponse<ReplyResponseDTO>> modifyReply(
            @PathVariable Long replyId,
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody ReplyCreateRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(communityService.modifyReply(replyId, principal.getMemberId(), requestDTO), "댓글 수정 완료")
        );
    }

    // POST /api/community/replies/{replyId}/like
    @Operation(summary = "댓글 좋아요 토글", description = "좋아요가 없으면 추가(+1), 있으면 취소(-1)합니다.")
    @PostMapping("/replies/{replyId}/like")
    public ResponseEntity<ApiResponse<Integer>> toggleReplyLike(
            @PathVariable Long replyId,
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(communityService.toggleReplyLike(replyId, principal.getMemberId()), "댓글 좋아요 처리 완료")
        );
    }

    /**
     * ─────────────────────────────────────────────────────
     * 작성자: 손민정
     * 작성일: 2026-05-13
     * 설명: 커뮤니티 인기글 조회 API
     * ─────────────────────────────────────────────────────
     */
    @Operation(summary = "인기글 조회", description = "Redis ZSet에서 높은 score 순으로 인기글 반환")
    @GetMapping("/posts/popular")
    public ResponseEntity<ApiResponse<List<PopularPostDTO>>> viewPopularCommunityPosts(
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PopularPostDTO> popularPosts = popularPostService.getPopularPosts(size);
        return ResponseEntity.ok(ApiResponse.ok(popularPosts, "인기글 조회 완료"));
    }
}
