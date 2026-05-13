package com.re_form_shop_2605.controller.community;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.community.*;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.service.community.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    // GET /api/community
    @Operation(summary = "커뮤니티 게시글 목록 조회", description = "종목 필터와 페이지 정보를 기준으로 게시글 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CommunityPostListItemDTO>>> redPosts(
            @RequestParam(required = false) Sport sport,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size
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
            @RequestHeader(value = "X-Member-Id", required = false) Long memberId
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(communityService.readPost(commId, memberId), "게시글 상세 조회 완료")
        );
    }

    // POST /api/community
    @Operation(summary = "커뮤니티 게시글 작성", description = "새 게시글을 작성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<CommIdResponseDTO>> addPost(
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @RequestBody CommunityPostCreateRequestDTO requestDTO
    ) {
        Long commId = communityService.addPost(memberId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(new CommIdResponseDTO(commId), "게시글 작성 완료"));
    }

    // PUT /api/community/{commId}
    @Operation(summary = "커뮤니티 게시글 수정", description = "본인이 작성한 게시글을 수정합니다.")
    @PutMapping("/{commId}")
    public ResponseEntity<ApiResponse<CommIdResponseDTO>> modifyPost(
            @PathVariable Long commId,
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @RequestBody CommunityPostUpdateRequestDTO requestDTO
    ) {
        communityService.modifyPost(commId, memberId, requestDTO);
        return ResponseEntity.ok(
                ApiResponse.ok(new CommIdResponseDTO(commId), "게시글 수정 완료")
        );
    }

    // DELETE /api/community/{commId}
    @Operation(summary = "커뮤니티 게시글 삭제", description = "본인이 작성한 게시글을 삭제합니다.")
    @DeleteMapping("/{commId}")
    public ResponseEntity<ApiResponse<Void>> removePost(
            @PathVariable Long commId,
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        communityService.removePost(commId, memberId);
        return ResponseEntity.ok(ApiResponse.ok(null, "게시글 삭제 완료"));
    }

    // POST /api/community/{commId}/like
    @Operation(summary = "커뮤니티 게시글 좋아요 토글", description = "좋아요가 없으면 추가(+1), 있으면 취소(-1)합니다.")
    @PostMapping("/{commId}/like")
    public ResponseEntity<ApiResponse<Integer>> toggleLike(
            @PathVariable Long commId,
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(communityService.toggleLike(commId, memberId), "좋아요 처리 완료")
        );
    }

    // GET /api/community/{commId}/replies
    @Operation(summary = "댓글 목록 조회", description = "게시글의 댓글과 대댓글 목록을 조회합니다. 작성자는 익명 처리됩니다.")
    @GetMapping("/{commId}/replies")
    public ResponseEntity<ApiResponse<List<ReplyResponseDTO>>> readReplies(
            @PathVariable Long commId,
            @RequestHeader(value = "X-Member-Id", required = false) Long memberId
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(communityService.readReplies(commId, memberId), "댓글 목록 조회 완료")
        );
    }

    // POST /api/community/{commId}/replies
    @Operation(summary = "댓글 작성", description = "게시글에 댓글 또는 대댓글을 작성합니다. parentId = null이면 최상위 댓글, 값이 있으면 대댓글입니다.")
    @PostMapping("/{commId}/replies")
    public ResponseEntity<ApiResponse<ReplyResponseDTO>> addReply(
            @PathVariable Long commId,
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @RequestBody ReplyCreateRequestDTO requestDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(
                        communityService.addReply(commId, memberId, requestDTO), "댓글 작성 완료"
                ));
    }

    // DELETE /api/community/replies/{replyId}
    @Operation(summary = "댓글 삭제", description = "본인이 작성한 댓글을 삭제합니다.")
    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<ApiResponse<Void>> removeReply(
            @PathVariable Long replyId,
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        communityService.removeReply(replyId, memberId);
        return ResponseEntity.ok(ApiResponse.ok(null, "댓글 삭제 완료"));
    }

    // POST /api/community/replies/{replyId}/like
    @Operation(summary = "댓글 좋아요 토글", description = "좋아요가 없으면 추가(+1), 있으면 취소(-1)합니다.")
    @PostMapping("/replies/{replyId}/like")
    public ResponseEntity<ApiResponse<Integer>> toggleReplyLike(
            @PathVariable Long replyId,
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(communityService.toggleReplyLike(replyId, memberId), "댓글 좋아요 처리 완료")
        );
    }
}
