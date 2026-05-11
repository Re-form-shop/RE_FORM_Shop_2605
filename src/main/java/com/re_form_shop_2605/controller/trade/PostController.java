package com.re_form_shop_2605.controller.trade;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.trade.PostCardDTO;
import com.re_form_shop_2605.dto.trade.PostCreateFormDTO;
import com.re_form_shop_2605.dto.trade.PostDetailDTO;
import com.re_form_shop_2605.dto.trade.PostUpdateFormDTO;
import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.service.trade.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 판매글 작성, 조회, 수정, 삭제 API
@RestController
@RequestMapping("/api/listings")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // GET /api/listings
    // 판매글 목록을 페이지 단위로 조회
    @Operation(
            summary = "판매글 목록 조회",
            description = "스포츠 종목, 키워드, 거래 방식, 페이지 정보를 기준으로 판매글 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PostCardDTO>>> readListings(
            @RequestHeader(value = "X-Member-Id", required = false) Long memberId,
            @RequestParam(required = false) Sport sport,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) DeliveryType tradeType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ApiResponse.ok(postService.readAllPosts(memberId, page, size), "판매글 목록 조회 완료"));
    }

    // GET /api/listings/{id}
    // 판매글 상세 조회
    @Operation(
            summary = "판매글 상세 조회",
            description = "판매글 ID로 상세 정보와 작성자 기준의 부가 정보를 조회합니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDetailDTO>> readListing(
            @PathVariable("id") Long postId,
            @RequestHeader(value = "X-Member-Id", required = false) Long memberId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(postService.readPost(postId, memberId), "판매글 상세 조회 완료"));
    }

    // POST /api/listings
    // 판매글 필드와 첨부 이미지를 multipart form-data로 함께 등록
    @Operation(
            summary = "판매글 등록",
            description = "판매글 정보와 첨부 이미지를 multipart form-data 형식으로 전달받아 새 판매글을 등록합니다."
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<IdResponse>> addListing(
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @ModelAttribute PostCreateFormDTO requestDTO
    ) {
        Long postId = postService.addPost(memberId, requestDTO.toRequestDTO(), requestDTO.getImages());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(new IdResponse(postId), "판매글 작성 완료"));
    }

    // PUT /api/listings/{id}
    // 판매글 수정 필드와 첨부 이미지를 multipart form-data로 함께 수정
    @Operation(
            summary = "판매글 수정",
            description = "판매글 ID에 해당하는 게시글의 정보와 첨부 이미지를 수정합니다."
    )
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<IdResponse>> modifyListing(
            @PathVariable("id") Long postId,
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @ModelAttribute PostUpdateFormDTO requestDTO
    ) {
        postService.modifyPost(postId, memberId, requestDTO.toUpdateRequestDTO(), requestDTO.getImages());
        return ResponseEntity.ok(ApiResponse.ok(new IdResponse(postId), "판매글 수정 완료"));
    }

    // DELETE /api/listings/{id}
    // 판매글을 삭제 상태로 변경
    @Operation(
            summary = "판매글 삭제",
            description = "판매글 ID에 해당하는 게시글을 삭제 상태로 변경합니다."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> removeListing(
            @PathVariable("id") Long postId,
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        postService.removePost(postId, memberId);
        return ResponseEntity.ok(ApiResponse.ok(null, "판매글 삭제 완료"));
    }

    // 생성/수정 응답에서 사용하는 식별자 DTO
    public record IdResponse(Long id) {
    }
}
