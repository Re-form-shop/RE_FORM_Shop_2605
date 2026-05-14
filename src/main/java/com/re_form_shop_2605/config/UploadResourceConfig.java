package com.re_form_shop_2605.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 파일 업로드 설정
 * ─────────────────────────────────────────────────────
 */
@Configuration
public class UploadResourceConfig implements WebMvcConfigurer {

    private final String uploadRoot;

    public UploadResourceConfig(@Value("${spring.servlet.multipart.location}") String uploadRoot) {
        this.uploadRoot = uploadRoot;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadPostRoot = Paths.get(uploadRoot).toAbsolutePath().normalize().resolve("post");
        Path uploadMemberRoot = Paths.get(uploadRoot).toAbsolutePath().normalize().resolve("member");
        registry.addResourceHandler("/uploads/post/**")
                .addResourceLocations(uploadPostRoot.toUri().toString() + "/");
        registry.addResourceHandler("/uploads/member/**")
                .addResourceLocations(uploadMemberRoot.toUri().toString() + "/");
    }
}
