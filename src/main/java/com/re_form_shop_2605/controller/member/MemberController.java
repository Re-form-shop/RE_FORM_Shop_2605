package com.re_form_shop_2605.controller.member;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.dto.member.OnboardingRequestDTO;
import com.re_form_shop_2605.dto.member.OnboardingResponseDTO;
import com.re_form_shop_2605.dto.member.ProfileResponseDTO;
import com.re_form_shop_2605.dto.member.ProfileUpdateRequestDTO;
import com.re_form_shop_2605.dto.trade.ReviewResponseDTO;
import com.re_form_shop_2605.service.member.InterestSettingService;
import com.re_form_shop_2605.service.member.MemberImageService;
import com.re_form_shop_2605.service.member.MemberService;
import com.re_form_shop_2605.service.trade.TradeService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 마이페이지와 관심 목록 관련 사용자 API
 * ─────────────────────────────────────────────────────
 */
@RestController
@RequestMapping("/api/users/me")
@Tag(name = "사용자 API", description = "마이페이지와 관심 목록 관련 API")
public class MemberController {

    private final MemberService memberService;
    private final MemberImageService memberImageService;
    private final InterestSettingService interestSettingService;
    private final TradeService tradeService;

    public MemberController(
            MemberService memberService,
            MemberImageService memberImageService,
            InterestSettingService interestSettingService,
            TradeService tradeService
    ) {
        this.memberService = memberService;
        this.memberImageService = memberImageService;
        this.interestSettingService = interestSettingService;
        this.tradeService = tradeService;
    }

    // GET /api/users/me
    // 현재 로그인 사용자의 프로필을 조회
    @Operation(
            summary = "내 프로필 조회",
            description = "현재 로그인한 회원의 프로필 정보를 조회합니다."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> readMyProfile(
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        return ResponseEntity.ok(ApiResponse.ok(memberService.readProfile(principal.getMemberId()), "내 프로필 조회 완료"));
    }

    // PATCH /api/users/me
    // 현재 로그인 사용자의 프로필을 수정
    @Operation(
            summary = "내 프로필 수정",
            description = "현재 로그인한 회원의 프로필 정보를 JSON 형식으로 수정합니다. 프로필 이미지는 별도 업로드 API로 URL을 먼저 받아 사용합니다."
    )
    @PatchMapping
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> modifyMyProfile(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody ProfileUpdateRequestDTO requestDTO
    ) {
        memberService.modifyProfile(principal.getMemberId(), requestDTO);
        return ResponseEntity.ok(ApiResponse.ok(memberService.readProfile(principal.getMemberId()), "프로필 수정 완료"));
    }

    // POST /api/users/me/profile-image
    // 프로필 수정 전에 이미지를 먼저 업로드하고 profileImageUrl로 사용할 URL을 반환
    @Operation(
            summary = "프로필 이미지 업로드",
            description = "프로필 수정 전에 이미지 파일을 먼저 업로드하고, 이후 프로필 수정 JSON의 profileImageUrl에 넣을 URL을 반환합니다."
    )
    @PostMapping(value = "/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProfileImageUploadResponse>> uploadProfileImage(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Parameter(
                    description = "업로드할 프로필 이미지 파일",
                    required = true,
                    schema = @Schema(type = "string", format = "binary")
            )
            @RequestPart("profileImage") MultipartFile profileImage
    ) {
        String profileImageUrl = memberImageService.saveTemporaryProfileImage(principal.getMemberId(), profileImage);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(new ProfileImageUploadResponse(profileImageUrl), "프로필 이미지 업로드 완료"));
    }

    // POST /api/users/me/interest-setting
    // 현재 로그인 사용자의 관심 설정을 저장
    @Operation(
            summary = "관심 설정 저장",
            description = "현재 로그인한 회원의 관심 종목과 온보딩 설정을 저장합니다."
    )
    @PostMapping("/interest-setting")
    public ResponseEntity<ApiResponse<Void>> saveInterestSetting(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @Valid @RequestBody OnboardingRequestDTO requestDTO
    ) {
        interestSettingService.saveOnboarding(principal.getMemberId(), requestDTO);
        return ResponseEntity.ok(ApiResponse.ok(null, "저장 완료"));
    }

    // GET /api/users/me/interest-setting
    // 현재 로그인 사용자의 관심 설정을 조회
    @Operation(
            summary = "관심 설정 조회",
            description = "현재 로그인한 회원의 관심 종목과 온보딩 설정을 조회합니다."
    )
    @GetMapping("/interest-setting")
    public ResponseEntity<ApiResponse<OnboardingResponseDTO>> readInterestSetting(
            @AuthenticationPrincipal MemberSecurityDTO principal
    ) {
        return ResponseEntity.ok(ApiResponse.ok(interestSettingService.readOnboarding(principal.getMemberId()), "관심 설정 조회 완료"));
    }

    // GET /api/users/me/reviews
    // 현재 로그인 사용자가 받은 매너 리뷰 목록을 조회
    @Operation(
            summary = "받은 매너 리뷰 조회",
            description = "현재 로그인한 회원이 받은 매너 리뷰 목록을 페이지 단위로 조회합니다."
    )
    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<PageResponse<ReviewResponseDTO>>> readMyReviews(
            @AuthenticationPrincipal MemberSecurityDTO principal,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<ReviewResponseDTO> reviews = tradeService.readSellerReviews(principal.getMemberId(), page, size);
        return ResponseEntity.ok(ApiResponse.ok(reviews, "받은 매너 평가 조회 완료"));
    }

    // 프로필 이미지 업로드 응답
    public record ProfileImageUploadResponse(String profileImageUrl) {
    }
}
