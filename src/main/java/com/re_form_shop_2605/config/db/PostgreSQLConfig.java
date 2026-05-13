/**
 * 작성자: 손민정
 * 작성일: 2026-05-13
 * 설명: DB DataSource 충돌 방지
 *      - 오류 1) Spring AI PgVectorStore 자동 설정이 @Primary인 MariaDB에 접근
 *              => PGVectorStore 빈 직접 생성해 PostgreSQL DataSource 명시적 주입
 */
package com.re_form_shop_2605.config.db;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

@Configuration
public class PostgreSQLConfig {
    /* 벡터 데이터 (AI)
       - spring.datasource.postgres.* 값 읽어옴
     */
    @Bean(name = "postgresDataSource")
    public DataSource postgresDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/reform_vector");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        return dataSource;
    }

    @Bean
    public PgVectorStore vectorStore(EmbeddingModel embeddingModel,
                                     @Qualifier("postgresDataSource") DataSource dataSource) {
        return PgVectorStore.builder(new JdbcTemplate(dataSource), embeddingModel)
                .initializeSchema(true)
                .build();
    }
}