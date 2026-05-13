package com.re_form_shop_2605.config;

import com.re_form_shop_2605.security.CustomOAuth2UserService;
import com.re_form_shop_2605.security.JWT.CustomUserDetailsService;
import com.re_form_shop_2605.security.JWT.JwtAuthenticationFilter;
import com.re_form_shop_2605.security.JWT.JwtTokenProvider;
import com.re_form_shop_2605.security.RestAccessDeniedHandler;
import com.re_form_shop_2605.security.JWT.RestAuthenticationEntryPoint;
import com.re_form_shop_2605.security.handler.CustomSocialLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * 작성자: 민기
 * 작성일: 2026-05-11
 * 설명: 스프링 시큐리티 설정
 */
@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
// 애플리케이션 전반의 인증/인가 규칙과 스프링 시큐리티 메인 체인을 정의하는 설정 클래스
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.secret}")
    private String jwtSecret;

    @Value("${security.jwt.access-token-expiration-seconds}")
    private long accessTokenExpirationSeconds;

    @Value("${security.jwt.refresh-token-expiration-seconds}")
    private long refreshTokenExpirationSeconds;

    @Value("${app.auth.oauth-success-redirect-uri}")
    private String oauthSuccessRedirectUri;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적 파일은 시큐리티 필터 자체를 타지 않게 제외해 불필요한 인증 비용을 줄인다.
        log.info("--- [SecurityConfig] webSecurityCustomizer: 정적 리소스 보안 제외 설정 ---");
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/webjars/**");
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        // JWT 구현체가 흩어진 @Value에 직접 의존하지 않도록 SecurityConfig에서 한 번에 조립한다.
        return new JwtTokenProvider(jwtSecret, accessTokenExpirationSeconds, refreshTokenExpirationSeconds);
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        // Authorization 헤더의 Bearer access token을 검사할 필터를 등록한다.
        return new JwtAuthenticationFilter(jwtTokenProvider(), customUserDetailsService);
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        // 인증되지 않은 요청을 JSON 403 응답으로 바꿔 줄 진입점 핸들러를 등록한다.
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public RestAccessDeniedHandler restAccessDeniedHandler() {
        // 인증은 되었지만 권한이 부족한 요청을 JSON 403 응답으로 바꿔 줄 핸들러를 등록한다.
        return new RestAccessDeniedHandler();
    }

    @Bean
    public CustomSocialLoginSuccessHandler customSocialLoginSuccessHandler(
            com.re_form_shop_2605.service.login.AuthTokenService authTokenService
    ) {
        // OAuth2 로그인 성공 시 JWT를 발급하고 프론트 콜백으로 리다이렉트할 핸들러를 등록한다.
        return new CustomSocialLoginSuccessHandler(authTokenService, oauthSuccessRedirectUri);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CustomSocialLoginSuccessHandler customSocialLoginSuccessHandler
    ) throws Exception {
        log.info("--- [SecurityConfig] securityFilterChain: 회원 시큐리티 설정 ---");

        http
                // 인증 진입점과 공개 조회 경로만 열고 나머지는 JWT 인증을 요구한다.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/api/auth/login",
                                "/api/auth/register",
                                "/api/auth/check-nickname",
                                "/api/auth/token/refresh",
                                "/api/auth/password/reset",
                                "/api/auth/oauth2/**",
                                "/api/auth/oauth/**",
                                "/oauth2/**",
                                "/login/oauth2/**",
                                "/login/**",
                                "/api/delivery/tracking/**",
                                "/uploads/**",
                                "/confirm"
                        ).permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/listings", "/api/listings/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                // csrf 토큰을 사용 안함
                .csrf(csrf -> csrf.disable())
                // JWT를 쓰므로 서버 세션은 만들지 않는다.
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .oauth2Login(oauth2 -> oauth2
                        // 카카오/구글 사용자 정보를 내부 회원 계정과 매핑한다.
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService))
                        // OAuth2 로그인 성공 시 자체 JWT를 발급해 프론트 콜백으로 보낸다.
                        .successHandler(customSocialLoginSuccessHandler)
                )
                // 인증 실패/권한 부족 응답을 브라우저 기본 HTML 대신 JSON 403으로 고정한다.
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(restAuthenticationEntryPoint())
                        .accessDeniedHandler(restAccessDeniedHandler())
                )

                // 컨트롤러 진입 전에 Authorization 헤더의 access token을 먼저 검사한다.
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // 이메일/비밀번호 로그인 시 사용할 인증 provider를 구성한다.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        // DB 비밀번호와 로그인 요청 비밀번호 비교에 BCrypt 인코더를 사용한다.
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        // 서비스 계층에서 직접 authenticate() 호출할 수 있도록 AuthenticationManager를 노출한다.
        return authenticationConfiguration.getAuthenticationManager();
    }
}
