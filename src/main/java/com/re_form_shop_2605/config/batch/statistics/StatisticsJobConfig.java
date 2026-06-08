package com.re_form_shop_2605.config.batch.statistics;

import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.trade.PostRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import com.re_form_shop_2605.repository.trade.mannerReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-27
 * 설명: 메인화면 통계 집계 배치
 *      - 누적 거래, 등록 상품, 활성 회원, 만족도 집계
 *      - 매일 자정 실행, Redis에 저장 (TTL 24시간)
 * ─────────────────────────────────────────────────────
 */

@Configuration
@RequiredArgsConstructor
public class StatisticsJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final TradeRepository tradeRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final mannerReviewRepository mannerReviewRepository;
    private final RedisTemplate<String, String> redisTemplate;

    // 1. Job
    @Bean
    public Job statisticsJob() {
        return new JobBuilder("statisticsJob", jobRepository)
                .start(statisticsStep())
                .build();
    }

    // 2. Step
    @Bean
    public Step statisticsStep() {
        return new StepBuilder("statisticsStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    // 1) status가 CONFIRMED인 거래 누적 건 수
                    long tradeCount = tradeRepository.countByStatus(TradeStatus.CONFIRMED);

                    // 2) status가 DELETED, HIDDEN이 아닌 등록 상품 누적 건 수
                    long productCount = postRepository.countByStatusNotIn(
                            List.of(PostStatus.DELETED, PostStatus.HIDDEN));

                    // 3) status가 ACTIVE인 활성 회원 수
                    long memberCount = memberRepository.countByStatus(MemberStatus.ACTIVE);

                    // 4) 만족도 (평균 score % 변환)
                    Double avgScore = mannerReviewRepository.findAverageScore();
                    long satisfaction = avgScore != null ? Math.round(avgScore / 5 * 100) : 0;

                    // 5) Redis 저장
                    redisTemplate.opsForValue().set("stats:trade_count", String.valueOf(tradeCount), 24, TimeUnit.HOURS);
                    redisTemplate.opsForValue().set("stats:product_count", String.valueOf(productCount), 24, TimeUnit.HOURS);
                    redisTemplate.opsForValue().set("stats:member_count", String.valueOf(memberCount), 24, TimeUnit.HOURS);
                    redisTemplate.opsForValue().set("stats:satisfaction", String.valueOf(satisfaction), 24, TimeUnit.HOURS);

                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }
}