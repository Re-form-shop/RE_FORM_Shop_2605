package com.re_form_shop_2605.service.login;

import com.re_form_shop_2605.dto.login.AuthUserDTO;
import com.re_form_shop_2605.dto.login.LoginResponseDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.security.JWT.JwtTokenProvider;
import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.UUID;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: 인증 토큰 저장, 조회, 삭제를 담당하는 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
@Service
@RequiredArgsConstructor
@Log4j2
// 로그인 성공 직후 access/refresh token 발급과 Redis 세션 저장을 전담하는 서비스 구현체
public class AuthTokenServiceImpl implements AuthTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MemberRepository memberRepository;

    @Override
    public LoginResponseDTO issueTokens(MemberSecurityDTO principal) {
        // 로그인마다 새로운 세션 ID를 발급해 브라우저/기기별 세션을 분리한다.
        log.info("[AuthTokenServiceImpl] issueTokens start memberId={} provider={}",
                principal.getMemberId(),
                principal.getLoginProvider());
        return issueTokens(principal, UUID.randomUUID().toString());
    }

    @Override
    public LoginResponseDTO issueTokens(MemberSecurityDTO principal, String sessionId) {
        // 이메일 로그인과 소셜 로그인 성공 지점에서 동일한 토큰 발급 규칙을 재사용한다.
        String accessToken = jwtTokenProvider.generateAccessToken(principal, sessionId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(principal, sessionId);
        saveRefreshToken(principal.getMemberId(), sessionId, refreshToken);
        log.info("[AuthTokenServiceImpl] issueTokens end memberId={} sessionId={} provider={}",
                principal.getMemberId(),
                sessionId,
                principal.getLoginProvider());
        return new LoginResponseDTO(accessToken, refreshToken, toAuthUserDTO(principal));
    }

    @Override
    public void blacklistAccessToken(String accessToken) {
        // 액세스 토큰의 남은 유효 시간을 계산해 그만큼만 Redis에 블랙리스트로 등록한다.
        long expirationTime = jwtTokenProvider.getClaims(accessToken).getExpiration().getTime();
        long now = System.currentTimeMillis();
        long ttl = expirationTime - now;

        if (ttl > 0) {
            redisTemplate.opsForValue().set(
                    "auth:blacklist:" + accessToken,
                    "logout",
                    ttl,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    @Override
    public boolean isAccessTokenBlacklisted(String accessToken) {
        // 블랙리스트 키가 존재하면 이미 로그아웃된 토큰으로 간주한다.
        return Boolean.TRUE.equals(redisTemplate.hasKey("auth:blacklist:" + accessToken));
    }

    private AuthUserDTO toAuthUserDTO(MemberSecurityDTO principal) {
        // 응답에 담을 사용자 정보는 DB 기준 최신 상태를 다시 조회해 조립한다.
        Member member = memberRepository.findById(principal.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        return new AuthUserDTO(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getRole(),
                member.getMannerScore()
        );
    }

    private void saveRefreshToken(Long memberId, String sessionId, String refreshToken) {
        // refresh token은 회원+세션별 키로 저장해 멀티 디바이스 로그인과 세션별 로그아웃을 지원한다.
        redisTemplate.opsForValue().set(
                buildRefreshTokenKey(memberId, sessionId),
                refreshToken,
                jwtTokenProvider.getRefreshTokenExpirationSeconds(),
                TimeUnit.SECONDS
        );
        // 회원별 세션 ID 집합을 별도로 유지해 전체 로그아웃/세션 목록 조회 시 KEYS 명령을 피한다.
        redisTemplate.opsForSet().add(buildSessionSetKey(memberId), sessionId);
        redisTemplate.expire(buildSessionSetKey(memberId), jwtTokenProvider.getRefreshTokenExpirationSeconds(), TimeUnit.SECONDS);
    }

    private String buildRefreshTokenKey(Long memberId, String sessionId) {
        // 세션별 토큰 키 규칙을 고정해 운영 중 추적과 삭제를 단순화한다.
        return "auth:refresh:" + memberId + ":" + sessionId;
    }

    private String buildSessionSetKey(Long memberId) {
        // 회원별 세션 목록 인덱스를 따로 유지해 세션 관리 기능을 효율적으로 구현한다.
        return "auth:sessions:" + memberId;
    }
}
