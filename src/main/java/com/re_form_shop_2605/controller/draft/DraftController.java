package com.re_form_shop_2605.controller.draft;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.draft.PostDraftDTO;
import com.re_form_shop_2605.dto.draft.ReplyDraftDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.service.draft.DraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 게시글 및 댓글 자동 저장 controller
 * ─────────────────────────────────────────────────────
 */
@Validated
@RestController
@RequestMapping("/api/drafts")
@Tag(name = "임시저장 API", description = "게시글 및 댓글 임시 저장 API")
public class DraftController {

    private final DraftService draftService;

    public DraftController(DraftService draftService) {
        this.draftService = draftService;
    }

    // PATCH /api/drafts/posts
    // 게시글 작성 중인 초안을 저장.
    @Operation(
            summary = "게시글 초안 저장",
            description = "현재 회원이 작성 중인 게시글 초안을 임시 저장합니다."
    )
    @PatchMapping("/posts")
    public ResponseEntity<ApiResponse<Void>> savePostDraft(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody PostDraftDTO requestDTO
    ) {
        draftService.savePostDraft(principal.getMemberId(), requestDTO);
        return ResponseEntity.ok(ApiResponse.ok(null, "게시글 초안 저장 완료"));
    }

    // GET /api/drafts/posts
    // 게시글 작성 중인 초안을 조회.
    @Operation(
            summary = "게시글 초안 조회",
            description = "현재 회원이 저장한 게시글 작성 초안을 조회합니다."
    )
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<PostDraftDTO>> readPostDraft(
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        return ResponseEntity.ok(ApiResponse.ok(draftService.getPostDraft(principal.getMemberId()), "게시글 초안 조회 완료"));
    }

    // DELETE /api/drafts/posts
    // 게시글 작성 중인 초안을 삭제.
    @Operation(
            summary = "게시글 초안 삭제",
            description = "현재 회원이 저장한 게시글 작성 초안을 삭제합니다."
    )
    @DeleteMapping("/posts")
    public ResponseEntity<ApiResponse<Void>> removePostDraft(
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        draftService.removePostDraft(principal.getMemberId());
        return ResponseEntity.ok(ApiResponse.ok(null, "게시글 초안 삭제 완료"));
    }

    // PATCH /api/drafts/replies
    // 댓글 작성 중인 초안을 저장.
    @Operation(
            summary = "댓글 초안 저장",
            description = "대상 종류와 대상 ID 기준으로 현재 회원의 댓글 작성 초안을 임시 저장합니다."
    )
    @PatchMapping("/replies")
    public ResponseEntity<ApiResponse<Void>> saveReplyDraft(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody ReplyDraftDTO requestDTO
    ) {
        draftService.saveReplyDraft(principal.getMemberId(), requestDTO);
        return ResponseEntity.ok(ApiResponse.ok(null, "댓글 초안 저장 완료"));
    }

    // GET /api/drafts/replies
    // 댓글 작성 중인 초안을 조회.
    @Operation(
            summary = "댓글 초안 조회",
            description = "대상 종류와 대상 ID 기준으로 현재 회원이 저장한 댓글 초안을 조회합니다."
    )
    @GetMapping("/replies")
    public ResponseEntity<ApiResponse<ReplyDraftDTO>> readReplyDraft(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam @NotBlank String targetType,
            @RequestParam @NotNull Long targetId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                draftService.getReplyDraft(principal.getMemberId(), targetType, targetId),
                "댓글 초안 조회 완료"
        ));
    }

    // DELETE /api/drafts/replies
    // 댓글 작성 중인 초안을 삭제.
    @Operation(
            summary = "댓글 초안 삭제",
            description = "대상 종류와 대상 ID 기준으로 현재 회원이 저장한 댓글 초안을 삭제합니다."
    )
    @DeleteMapping("/replies")
    public ResponseEntity<ApiResponse<Void>> removeReplyDraft(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam @NotBlank String targetType,
            @RequestParam @NotNull Long targetId
    ) {
        draftService.removeReplyDraft(principal.getMemberId(), targetType, targetId);
        return ResponseEntity.ok(ApiResponse.ok(null, "댓글 초안 삭제 완료"));
    }
}
