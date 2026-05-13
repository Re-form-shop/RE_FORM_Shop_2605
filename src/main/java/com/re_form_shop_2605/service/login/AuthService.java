package com.re_form_shop_2605.service.login;

import com.re_form_shop_2605.dto.login.AuthUserDTO;
import com.re_form_shop_2605.dto.login.AuthSessionResponseDTO;
import com.re_form_shop_2605.dto.login.LoginRequestDTO;
import com.re_form_shop_2605.dto.login.LoginResponseDTO;
import com.re_form_shop_2605.dto.login.LogoutRequestDTO;
import com.re_form_shop_2605.dto.login.LogoutSessionRequestDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.dto.login.PasswordResetRequestDTO;
import com.re_form_shop_2605.dto.login.TokenRefreshRequestDTO;
import com.re_form_shop_2605.dto.login.TokenRefreshResponseDTO;
import com.re_form_shop_2605.dto.member.MemberResponseDTO;

import java.util.List;
/**
 * 작성자: 민기
 * 작성일: 2026-05-11
 * 설명:
 */
// 회원 인증/인가 서비스 인터페이스
public interface AuthService {

    // 로그인 한 회원 정보와 토큰을 반환
    LoginResponseDTO login(LoginRequestDTO requestDTO);

    // 회원가입 직후 사용자 정보를 로드해 토큰을 반환
    MemberResponseDTO register(String email, String password, String nickname, boolean marketingAgreed);

    // access token 인증된 사용자의 최신 회원 정보를 응답 DTO로 반환
    AuthUserDTO readMe(MemberSecurityDTO principal);

    // 현재 로그인한 사용자의 모든 세션 정보를 조회
    List<AuthSessionResponseDTO> readSessions(MemberSecurityDTO principal, String accessToken);

    // 사용자에게 저장된 리프레쉬 토큰을 삭제 후 로그아웃
    void logout(MemberSecurityDTO principal, LogoutRequestDTO requestDTO);

    // 현재 사용자의 특정 세션 하나만 종료
    void logoutSession(MemberSecurityDTO principal, LogoutSessionRequestDTO requestDTO);

    // 현재 사용자에게 연결된 모든 세션 종료
    void logoutAll(MemberSecurityDTO principal);

    // 비밀번호 재설정
    void resetPassword(PasswordResetRequestDTO requestDTO);

    // refresh token 재발급
    TokenRefreshResponseDTO refresh(TokenRefreshRequestDTO requestDTO);
}
