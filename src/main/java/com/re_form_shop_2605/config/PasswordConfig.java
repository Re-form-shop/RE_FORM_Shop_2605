package com.re_form_shop_2605.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * 작성자: 민기
 * 작성일: 2026-05-11
 * 설명: 비밀번호 암호화
 */
@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 인증/회원 기능 전반에서 공통으로 사용할 비밀번호 인코더를 별도 설정으로 분리한다.
        return new BCryptPasswordEncoder();
    }
}
