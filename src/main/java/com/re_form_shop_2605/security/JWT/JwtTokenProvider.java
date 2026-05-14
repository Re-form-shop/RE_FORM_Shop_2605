package com.re_form_shop_2605.security.JWT;

import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: JWT 생성, 검증, claim 추출을 담당하는 보안 유틸리티
 * ─────────────────────────────────────────────────────
 */
// JWT 생성/검증과 claim 추출만 담당하는 순수 유틸리티성 보안 객체
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenExpirationSeconds;
    // Redis에 refresh token TTL을 맞춰 저장할 때 사용한다.
    @Getter
    private final long refreshTokenExpirationSeconds;

    // JWT 서명 키와 access/refresh 만료 시간을 설정값에서 읽어 초기화한다.
    public JwtTokenProvider(String secret, long accessTokenExpirationSeconds, long refreshTokenExpirationSeconds) {
        // 외부 설정값은 config에서 정리하고, 여기서는 실제 JJWT 서명 키 객체만 만든다.
        String encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedSecret));
        this.accessTokenExpirationSeconds = accessTokenExpirationSeconds;
        this.refreshTokenExpirationSeconds = refreshTokenExpirationSeconds;
    }

    public String generateAccessToken(MemberSecurityDTO principal) {
        // access token은 짧은 만료 시간으로 API 호출 인증에 사용한다.
        return generateAccessToken(principal, UUID.randomUUID().toString());
    }

    public String generateAccessToken(MemberSecurityDTO principal, String sessionId) {
        // access token과 refresh token은 같은 sessionId를 공유해 세션 단위 추적이 가능해야 한다.
        return buildToken(principal, accessTokenExpirationSeconds, "access", sessionId);
    }

    public String generateRefreshToken(MemberSecurityDTO principal) {
        // refresh token은 access token 재발급 전용으로 분리한다.
        return generateRefreshToken(principal, UUID.randomUUID().toString());
    }

    public String generateRefreshToken(MemberSecurityDTO principal, String sessionId) {
        // refresh token 역시 같은 sessionId로 발급해 Redis 키와 1:1 매핑한다.
        return buildToken(principal, refreshTokenExpirationSeconds, "refresh", sessionId);
    }

    public boolean validateToken(String token) {
        // 검증 실패 시 JJWT 예외를 그대로 던져 호출부에서 응답 형태를 결정하게 한다.
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        return true;
    }

    public Claims getClaims(String token) {
        // claim 기반 검증이나 memberId 추출을 위해 payload를 그대로 반환한다.
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    public String getSubject(String token) {
        // subject에는 이메일을 저장해 사용자 재조회 키로 사용한다.
        return getClaims(token).getSubject();
    }

    public Long getMemberId(String token) {
        // 토큰의 memberId claim을 숫자로 꺼내 컨트롤러 호환 헤더와 refresh 검증에 재사용한다.
        Number memberId = getClaims(token).get("memberId", Number.class);
        return memberId.longValue();
    }

    public String getTokenType(String token) {
        // access/refresh 구분을 위해 type claim을 읽는다.
        return getClaims(token).get("type", String.class);
    }

    public String getSessionId(String token) {
        // 세션별 로그아웃과 refresh rotation을 위해 sessionId를 꺼낸다.
        return getClaims(token).get("sessionId", String.class);
    }

    public String getLoginProvider(String token) {
        // local / kakao / google 같은 로그인 제공자 식별값을 반환한다.
        return getClaims(token).get("provider", String.class);
    }

    public String getLoginType(String token) {
        // local / social 같은 상위 로그인 타입을 반환한다.
        return getClaims(token).get("loginType", String.class);
    }

    public List<String> getRoles(String token) {
        // 여러 권한이 쉼표로 저장된 경우를 대비해 리스트로 반환한다.
        String roles = getClaims(token).get("role", String.class);
        if (roles == null || roles.isBlank()) {
            return List.of();
        }
        return List.of(roles.split(","));
    }

    private String buildToken(MemberSecurityDTO principal, long expirationSeconds, String type, String sessionId) {
        // 이메일은 subject, 회원 식별값과 권한은 claim으로 넣어 후속 인증에 재사용한다.
        Instant now = Instant.now();
        String loginProvider = principal.getLoginProvider();
        String loginType = "local".equalsIgnoreCase(loginProvider) ? "local" : "social";

        // 다중 권한을 지원하기 위해 모든 권한을 쉼표로 연결해 저장한다.
        String roles = principal.getAuthorities().stream()
                .map(Object::toString)
                .reduce((a, b) -> a + "," + b)
                .orElse("ROLE_USER");

        return Jwts.builder()
                .subject(principal.getEmail())
                .claim("memberId", principal.getMemberId())
                .claim("role", roles)
                .claim("type", type)
                .claim("provider", loginProvider)
                .claim("loginType", loginType)
                .claim("sessionId", sessionId)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirationSeconds)))
                .signWith(secretKey)
                .compact();
    }
}
