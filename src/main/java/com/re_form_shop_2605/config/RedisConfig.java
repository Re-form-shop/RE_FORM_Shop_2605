package com.re_form_shop_2605.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.ObjectMapper;
/**
 * 작성자: 민기
 * 작성일: 2026-05-11
 * 설명: Redis DB 설정
 */
@Configuration
public class RedisConfig {
    // application.properties 에 정의한 Redis 서버 정보를 주입 받음
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
    public ObjectMapper redisObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(ObjectMapper redisObjectMapper) {
        // key 는 사람이 읽기 쉬운 문자열로 저장하고,value 는 JSON 으로 저장.
        // 이렇게 설정하면 DTO가 Serializable 을 구현하지 않아도 Redis 에 저장할 수 있음.

        // Redis value 는 JSON 문자열 기반으로 저장.
        GenericJacksonJsonRedisSerializer serializer = new GenericJacksonJsonRedisSerializer(redisObjectMapper);

        // 2. RedisTemplate 설정
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        // Redis key 는 일반 문자열 형태로 저장.
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);

        // template 설정 적용 후 반환.
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
