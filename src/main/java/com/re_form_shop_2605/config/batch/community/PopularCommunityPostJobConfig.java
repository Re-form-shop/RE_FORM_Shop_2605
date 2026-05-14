package com.re_form_shop_2605.config.batch.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.re_form_shop_2605.dto.batch.PopularPostDTO;
import com.re_form_shop_2605.entity.Enum.CommunityPostStatus;
import com.re_form_shop_2605.entity.community.CommunityPost;
import com.re_form_shop_2605.repository.community.CommunityPostRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-13
 * 설명: 커뮤니티 인기글 집계 배치
 *       - 1시간마다 실행 시간 기준 24시간 이내 게시글 조회
 *       - 결과는 Redis ZSet "popular:posts"에 저장
 * ─────────────────────────────────────────────────────
 */

@Configuration
@RequiredArgsConstructor
public class PopularCommunityPostJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final CommunityPostRepository communityPostRepository;
    private final RedisTemplate<String, String> redisTemplate; // Redis ZSet 저장, 조회, 삭제 등 작업

    @Bean
    public ObjectMapper objectMapper() { // Java 객체 <-> JSON 문자열
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // LocalDateTime JSON화
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    // 1. JOB
    @Bean
    public Job popularCommunityPostJob() {
        return new JobBuilder("popularCommunityPostJob", jobRepository)
                .start(calculatePopularityStep())
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        // 기존 데이터 삭제
                        redisTemplate.delete("popular:posts");
                    }
                })
                .build();
    }

    // 2. Step
    @Bean
    public Step calculatePopularityStep() {
        return new StepBuilder("calculatePopularityStep", jobRepository)
                .<CommunityPost, PopularPostDTO>chunk(10)
                .transactionManager(platformTransactionManager)
                .reader(new ItemReader<>() {
                    // Reader : 최근 24시간 게시글 전체 조회
                    private List<CommunityPost> posts;
                    private int index;

                    @Override
                    public @Nullable CommunityPost read() {
                        if (posts == null) {
                            posts = communityPostRepository.findRecentPosts(
                                    LocalDateTime.now().minusHours(24),
                                    CommunityPostStatus.ACTIVE
                            );
                        }
                        return index < posts.size() ? posts.get(index++) : null;
                    }
                })
                .processor(calculatePopularityProcess()) // post별 score 계산 -> DTO 변환
                .writer(savePopularCommunityPosts())     // 1시간마다 Redis 삭제 및 새 데이터 저장
                .build();
    }

    // 3. ItemProcessor
    @Bean
    public ItemProcessor<CommunityPost, PopularPostDTO> calculatePopularityProcess() {
        return post -> {
            // 1) score 계산
            int score = (post.getCommViewCount() * 1) + (post.getLikeCount() * 3) + (post.getCommentCount() * 2);

            // 2) CommunityPost -> PopularPostDTO 변환
            return new PopularPostDTO(
                    post.getCommId(), post.getCommTitle(), post.getSportCategory(), post.getTeamCategory(),
                    post.getCommViewCount(), post.getLikeCount(), post.getCommentCount(), score,
                    post.getMember().getNickname(), post.getMember().getProfileImageUrl(), post.getCreatedAt()
                    );
        };
    }

    // 5. ItemWriter
    @Bean
    public ItemWriter<PopularPostDTO> savePopularCommunityPosts() {
        return chunk -> {
            for (PopularPostDTO post : chunk.getItems()) {
                // 1) PopularPostDTO -> JSON 변환
                String json = objectMapper().writeValueAsString(post);

                // 2) Redis ZSet 저장
                redisTemplate.opsForZSet().add("popular:posts", json, post.score());
            }

            // 3) TTL 1시간 설정
            redisTemplate.expire("popular:posts", 1, TimeUnit.HOURS);
        };
    }
}