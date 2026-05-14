package com.re_form_shop_2605.config.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-12
 * 설명: MyBatis 수동 설정
 *       - JPA, Spring Batch DataSource와 충돌 방지를 위해 수동 설정
 *       - MariaDB DataSource 사용 (Primary)
 *       - 경로 따른 XML 매퍼 파일 인식
 *       - 카멜 케이스 자동 매핑 설정
 * ─────────────────────────────────────────────────────
 */

@Configuration
@MapperScan("com.re_form_shop_2605.mapper") // Mapper 인터페이스 패키지 경로
public class MyBatisConfig {
    /* MyBatis 수동 설정 */
    // JPA, Spring Batch 데이터소스와 충돌 방지 위해

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        // 1. 데이터 소스 연결
        sessionFactory.setDataSource(dataSource);

        // 2. XML 매퍼 파일 위치 설정 (properties의 mybatis.mapper-locations와 동일)
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));

        // 3. 카멜 케이스 등 추가 설정
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);

        return sessionFactory.getObject();
    }
}