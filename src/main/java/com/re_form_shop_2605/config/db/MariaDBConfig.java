/**
 * 작성자: 손민정
 * 작성일: 2026-05-13
 * 설명: DB DataSource 충돌 방지
 *      - 오류 1) ConfigurationProperties 사용 시 postgres.*까지 읽어 오류
 *              => HikariDataSource에 직접 값 넣어 해결
 */
package com.re_form_shop_2605.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class MariaDBConfig {
    /* 일반 데이터
       - Primary : 기본 DataSource (JPA, MyBatis)
       - spring.datasource.* 값 읽어옴
     */
    @Primary
    @Bean(name = "mariadbDataSource")
    public DataSource mariadbDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mariadb://localhost:3306/reform_shop_2605");
        dataSource.setUsername("admin");
        dataSource.setPassword("0507");
        return dataSource;
    }
}