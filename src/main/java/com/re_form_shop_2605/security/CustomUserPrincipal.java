package com.re_form_shop_2605.security;

import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: Spring Security 인증에 사용하는 사용자 principal 구현체
 * ─────────────────────────────────────────────────────
 */
public record CustomUserPrincipal(
        Long memberId,
        String email,
        String password,
        String nickname,
        String profileImageUrl,
        Role role,
        MemberStatus status
) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 회원 role 값을 Spring Security 권한 컬렉션으로 변환한다.
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        // 인증 비교에 사용할 암호화 비밀번호를 반환한다.
        return password;
    }

    @Override
    public String getUsername() {
        // 이 프로젝트에서는 이메일을 username 식별자로 사용한다.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 별도 만료 정책이 없어 항상 만료되지 않은 계정으로 본다.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 정지 회원만 로그인 차단하도록 잠금 여부를 계산한다.
        return status != MemberStatus.SUSPENDED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호 만료 정책이 없어 항상 유효한 자격 증명으로 처리한다.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 활성 상태 회원만 최종적으로 인증 가능하도록 한다.
        return status == MemberStatus.ACTIVE;
    }
}
