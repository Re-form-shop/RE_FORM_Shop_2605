package com.re_form_shop_2605.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 작성자: 민기
 * 작성일: 2026-05-08
 * 설명: swagger 설정
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi restApi() {
        return GroupedOpenApi.builder()
                .pathsToMatch("/api/**")
                .group("REST API")
                .build();
    }

    @Bean
    public GroupedOpenApi commonApi() {
        return GroupedOpenApi.builder()
                .pathsToMatch("/**/*")
                .pathsToExclude("/api/**/*")
                .group("COMMON API")
                .build();
    }
}