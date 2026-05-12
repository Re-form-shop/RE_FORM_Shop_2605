package com.re_form_shop_2605.config.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostgreSQLConfig {
    /* 벡터 데이터 (AI)
       - spring.datasource.postgres.* 값을 읽어옴
     */

    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.postgres")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }
}