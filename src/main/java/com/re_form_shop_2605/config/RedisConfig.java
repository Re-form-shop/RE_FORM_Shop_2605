package com.re_form_shop_2605.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-11
 * 설명: Redis DB 설정
 * ─────────────────────────────────────────────────────
 */
@Configuration
public class RedisConfig {
    // application.properties 에 정의한 Redis 서버 정보를 주입받음.
    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        /* Spring Data Redis가 실제 Redis 서버와 통신할 때 사용할 연결 팩토리 */
        // Boot 3.x 기본 클라이언트인 Lettuce 기반으로 생성
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        // key 는 사람이 읽기 쉬운 문자열로 저장하고,value 는 JSON 으로 저장.
        // 이렇게 설정하면 DTO가 Serializable 을 구현하지 않아도 Redis 에 저장할 수 있음.

        // 1. ObjectMapper 설정
        // Redis value 를 JSON 으로 직렬화/역직렬화할 때 사용할 serializer.
        // 현재 Spring Data Redis 버전에서는 builder 기반 JSON serializer를 사용.
        GenericJacksonJsonRedisSerializer serializer =
                GenericJacksonJsonRedisSerializer.builder().build();

        // 2. RedisTemplate 설정
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        // Redis key 는 일반 문자열 형태로 저장.
        template.setKeySerializer(new StringRedisSerializer());

        // Redis value 는 JSON 문자열 기반으로 저장.
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        // template 설정 적용 후 반환.
        template.afterPropertiesSet();
        return template;
    }
}
