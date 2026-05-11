package com.re_form_shop_2605.controller.draft;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.draft.PostDraftDTO;
import com.re_form_shop_2605.dto.draft.ReplyDraftDTO;
import com.re_form_shop_2605.service.draft.DraftService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/drafts")
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
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @RequestBody PostDraftDTO requestDTO
    ) {
        draftService.savePostDraft(memberId, requestDTO);
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
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(draftService.getPostDraft(memberId), "게시글 초안 조회 완료"));
    }

    // DELETE /api/drafts/posts
    // 게시글 작성 중인 초안을 삭제.
    @Operation(
            summary = "게시글 초안 삭제",
            description = "현재 회원이 저장한 게시글 작성 초안을 삭제합니다."
    )
    @DeleteMapping("/posts")
    public ResponseEntity<ApiResponse<Void>> removePostDraft(
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        draftService.removePostDraft(memberId);
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
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @RequestBody ReplyDraftDTO requestDTO
    ) {
        draftService.saveReplyDraft(memberId, requestDTO);
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
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestParam @NotBlank String targetType,
            @RequestParam @NotNull Long targetId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(
                draftService.getReplyDraft(memberId, targetType, targetId),
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
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestParam @NotBlank String targetType,
            @RequestParam @NotNull Long targetId
    ) {
        draftService.removeReplyDraft(memberId, targetType, targetId);
        return ResponseEntity.ok(ApiResponse.ok(null, "댓글 초안 삭제 완료"));
    }
}
