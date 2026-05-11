package com.re_form_shop_2605.controller.member;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.member.OnboardingRequestDTO;
import com.re_form_shop_2605.dto.member.OnboardingResponseDTO;
import com.re_form_shop_2605.dto.member.ProfileResponseDTO;
import com.re_form_shop_2605.dto.member.ProfileUpdateFormDTO;
import com.re_form_shop_2605.dto.trade.ReviewResponseDTO;
import com.re_form_shop_2605.service.member.InterestSettingService;
import com.re_form_shop_2605.service.member.MemberService;
import com.re_form_shop_2605.service.trade.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 마이페이지와 관심 목록 관련 사용자 API
@RestController
@RequestMapping("/api/users/me")
public class MemberController {

    private final MemberService memberService;
    private final InterestSettingService interestSettingService;
    private final TradeService tradeService;

    public MemberController(
            MemberService memberService,
            InterestSettingService interestSettingService,
            TradeService tradeService
    ) {
        this.memberService = memberService;
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
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(memberService.readProfile(memberId), "내 프로필 조회 완료"));
    }

    // PATCH /api/users/me
    // 현재 로그인 사용자의 프로필을 수정
    @Operation(
            summary = "내 프로필 수정",
            description = "현재 로그인한 회원의 프로필 정보와 프로필 이미지를 multipart form-data 형식으로 수정합니다."
    )
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> modifyMyProfile(
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @ModelAttribute ProfileUpdateFormDTO requestDTO
    ) {
        memberService.modifyProfile(memberId, requestDTO.toUpdateRequestDTO(), requestDTO.getProfileImage());
        return ResponseEntity.ok(ApiResponse.ok(memberService.readProfile(memberId), "프로필 수정 완료"));
    }

    // POST /api/users/me/interest-setting
    // 현재 로그인 사용자의 관심 설정을 저장
    @Operation(
            summary = "관심 설정 저장",
            description = "현재 로그인한 회원의 관심 종목과 온보딩 설정을 저장합니다."
    )
    @PostMapping("/interest-setting")
    public ResponseEntity<ApiResponse<Void>> saveInterestSetting(
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @RequestBody OnboardingRequestDTO requestDTO
    ) {
        interestSettingService.saveOnboarding(memberId, requestDTO);
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
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        return ResponseEntity.ok(ApiResponse.ok(interestSettingService.readOnboarding(memberId), "관심 설정 조회 완료"));
    }

    // GET /api/users/me/reviews
    // 현재 로그인 사용자가 받은 매너 리뷰 목록을 조회
    @Operation(
            summary = "받은 매너 리뷰 조회",
            description = "현재 로그인한 회원이 받은 매너 리뷰 목록을 페이지 단위로 조회합니다."
    )
    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<PageResponse<ReviewResponseDTO>>> readMyReviews(
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<ReviewResponseDTO> reviews = tradeService.readSellerReviews(memberId, page, size);
        return ResponseEntity.ok(ApiResponse.ok(reviews, "받은 매너 평가 조회 완료"));
    }
}
