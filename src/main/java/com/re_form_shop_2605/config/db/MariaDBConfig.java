package com.re_form_shop_2605.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 작성자: 손민정
 * 작성일: 2026-05-13
 * 설명: DB DataSource 충돌 방지
 *      - 오류 1) ConfigurationProperties 사용 시 postgres.*까지 읽어 오류
 *              => HikariDataSource에 직접 값 넣어 해결
 */

@Configuration
public class MariaDBConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /* 일반 데이터
       - Primary : 기본 DataSource (JPA, MyBatis)
       - spring.datasource.* 값만 사용해 접속 정보를 한 곳에서 관리한다.
     */
    @Primary
    @Bean(name = "mariadbDataSource")
    public DataSource mariadbDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
