package com.re_form_shop_2605.config.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource mariadbDataSource() {
        return DataSourceBuilder.create().build();
    }
}