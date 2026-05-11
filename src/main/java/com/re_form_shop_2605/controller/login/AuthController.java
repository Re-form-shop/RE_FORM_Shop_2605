package com.re_form_shop_2605.controller.login;

import com.re_form_shop_2605.dto.common.ApiResponse;
import com.re_form_shop_2605.dto.member.MemberRequestDTO;
import com.re_form_shop_2605.dto.member.MemberResponseDTO;
import com.re_form_shop_2605.dto.member.NicknameResponseDTO;
import com.re_form_shop_2605.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

// 회원가입과 닉네임 중복 확인 같은 인증 진입 API
@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final MemberService memberService;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
    }

    // POST /api/auth/register
    // 회원가입 요청을 받아 서비스에 위임
    @Operation(
            summary = "회원가입",
            description = "이메일, 비밀번호, 닉네임과 약관 동의 여부를 받아 새 회원을 등록합니다."
    )
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<MemberResponseDTO>> register(@Valid @RequestBody RegisterRequest request) {
        if (!request.agreeTerms() || !request.agreePrivacy()) {
            throw new IllegalArgumentException("필수 약관 동의가 필요합니다.");
        }

        MemberResponseDTO responseDTO = memberService.join(
                new MemberRequestDTO(
                        request.email(),
                        request.nickname(),
                        request.password(),
                        request.agreeMarketing()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(responseDTO, "회원가입이 완료되었습니다."));
    }

    // GET /api/auth/check-nickname
    // 닉네임 사용 가능 여부를 확인
    @Operation(
            summary = "닉네임 중복 확인",
            description = "입력한 닉네임의 사용 가능 여부를 확인합니다."
    )
    @GetMapping("/check-nickname")
    public ResponseEntity<ApiResponse<NicknameResponseDTO>> checkNickname(
            @RequestParam @NotBlank @Size(min = 2, max = 20) String nickname
    ) {
        return ResponseEntity.ok(ApiResponse.ok(memberService.checkNickname(nickname), "닉네임 확인 완료"));
    }

    // 프론트 회원가입 폼에서 전달하는 요청 본문
    public record RegisterRequest(
            @NotBlank @Email @Size(max = 100) String email,
            @NotBlank @Size(min = 8) String password,
            @NotBlank @Size(min = 2, max = 20) String nickname,
            boolean agreeTerms,
            boolean agreePrivacy,
            boolean agreeMarketing
    ) {
    }
}
