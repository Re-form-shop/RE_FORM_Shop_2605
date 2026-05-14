# 벡터 기능 관련 로컬 환경 설정 가이드

---

## 1. Docker pgvector 컨테이너 실행

### 처음 설치하는 경우
```bash
docker run -d \
  --name pgvector \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  pgvector/pgvector:pg16
```

### 이미 컨테이너가 있는 경우
```bash
docker start <컨테이너ID>
```

---

## 2. reform_vector DB 생성

```bash
docker exec -it <컨테이너ID> psql -U postgres -c "CREATE DATABASE reform_vector;"
```

---

## 3. PGVector 확장 설치

```bash
docker exec -it <컨테이너ID> psql -U postgres -d reform_vector -c "CREATE EXTENSION IF NOT EXISTS vector;"
```

설치 확인
```bash
docker exec -it <컨테이너ID> psql -U postgres -d reform_vector -c "SELECT * FROM pg_extension WHERE extname = 'vector';"
```

---

## 4. application.properties 추가

```properties
# PostgreSQL (PGVector용)
spring.datasource.postgres.url=jdbc:postgresql://localhost:5432/reform_vector
spring.datasource.postgres.username=postgres
spring.datasource.postgres.password=postgres
spring.datasource.postgres.driver-class-name=org.postgresql.Driver

# Spring AI PGVector
spring.ai.vectorstore.pgvector.initialize-schema=false
spring.ai.vectorstore.pgvector.dimensions=1536
spring.ai.vectorstore.pgvector.distance-type=COSINE_DISTANCE

# OpenAI
spring.ai.openai.api-key=${OPENAI_API_KEY}
```

---

## 5. .env 파일에 추가

프로젝트 루트의 `.env` 파일에 아래 항목 추가

```
OPENAI_API_KEY=키_입력되어_있는지_확인
```
> OpenAI API 키는 https://platform.openai.com 에서 발급

---

## 6. build.gradle 의존성 확인

아래 항목이 있는지 확인 (없으면 추가 후 Gradle 새로고침)

```groovy
runtimeOnly 'org.postgresql:postgresql'
implementation 'org.springframework.ai:spring-ai-starter-vector-store-pgvector'
implementation 'org.springframework.ai:spring-ai-starter-model-openai'
```

---

## 7. 추가된 Config 파일

| 파일 | 역할 |
|------|------|
| `MariaDBConfig.java` | MariaDB DataSource 수동 설정 (@Primary, JPA/MyBatis 기본 DB) |
| `PostgreSQLConfig.java` | PostgreSQL DataSource + PgVectorStore 빈 설정 (AI 벡터 검색용) |

> DB가 두 개라 Spring Boot 자동 설정 충돌 방지를 위해 수동 설정 필요
> MariaDBConfig에 @Primary 붙어있으므로 JPA/MyBatis는 MariaDB 사용

---

## 주의사항

- 서버 실행 전 반드시 Docker pgvector 컨테이너 실행
- `.env` 파일에 `OPENAI_API_KEY` 없으면 서버 실행 시 오류
- `reform_vector` DB가 없으면 서버 실행 시 오류