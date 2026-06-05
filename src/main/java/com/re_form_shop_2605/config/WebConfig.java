package com.re_form_shop_2605.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 작성자: 최민종
 * 작성일: 2026-05-14
 * 설명:
 * CORS(Cross-Origin Resource Sharing) 설정을 위한 구성 클래스
 * 서로 다른 도메인/포트(예: localhost:5173 -> localhost:8080) 간의 자원 공유를 허용
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로("/**")에 대해 CORS 설정을 적용
        registry.addMapping("/**")
                // 프론트엔드 개발 서버 주소인 http://localhost:5173의 접근을 허용
                .allowedOrigins("http://localhost:5173", "https://reform-view.duckdns.org")
                // GET, POST, PUT, DELETE와 같은 CRUD 작업 및 사전 요청(OPTIONS)을 허용
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                // 'Authorization': JWT 토큰을 담아 보낼 때 사용되는 필수 헤더
                // 'Content-Type': JSON 데이터 등을 주고받기 위해 필요한 헤더
                .allowedHeaders("Authorization", "Content-Type")
                // JWT를 쿠키에 담아 보내거나, 세션 인증 등을 병행할 때 true 설정이 필수
                // (주의: true일 경우 allowedOrigins에 "*"를 사용할 수 없음)
                .allowCredentials(true);
                // (선택 사항 추가 가능) 브라우저가 CORS 설정을 캐싱할 시간을 초 단위로 설정
                // .maxAge(3600);
    }
}