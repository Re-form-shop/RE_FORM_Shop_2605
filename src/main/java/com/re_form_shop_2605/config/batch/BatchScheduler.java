package com.re_form_shop_2605.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-13
 * 설명: 배치 스케줄러
 *       - 자동 구매 확정 및 미정산 거래 정산 처리 (매일 새벽 4시)
 *       - 커뮤니티 인기글 집계 (1시간마다)
 *       - 게시물 위험 탐지 배치 (6시간마다)
 *       - 메인 화면 통계 집계 배치 (매일 자정마다)
 * ─────────────────────────────────────────────────────
 */

@Component
@RequiredArgsConstructor
public class BatchScheduler {
    private final JobOperator jobOperator;
    private final Job dailyBatchJob;           // 자동구매 확정 + 정산 자동화
    private final Job popularCommunityPostJob; // 커뮤니티 인기글 조회
    private final Job riskDetectionJob;        // 위험 탐지 배치
    private final Job statisticsJob;           // 메인 화면 통계 배치

    /* 1. 자동 구매 확정 -> 미정산 거래 정산 처리 */
    // 1) 5일 경과된 미구매확정 거래 자동 구매확정 처리
    // 2) 구매자의 '구매 확정' 시, 판매자 point wallet으로 수수료 제외 포인트 지급
    @Scheduled(cron = "0 0 4 * * ?")
    public void runDailyBatchJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobOperator.start(dailyBatchJob, params);
    }

    /* 2. 1시간 간격 커뮤니티 게시물 인기글 집계 */
    @Scheduled(cron = "0 0 * * * *")
    public void runPopularCommunityPostJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobOperator.start(popularCommunityPostJob, params);
    }

    /* 3. 6시간 간격 게시글 위험 탐지 재검사 */
    // 미검사 게시글 및 신고 누적 3건 이상 게시글 AI 위험도 분석
    @Scheduled(cron = "0 0 */6 * * *")
    public void runRiskDetectionJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobOperator.start(riskDetectionJob, params);
    }

    /* 4. 매일 자정 메인 화면 통계 집계 */
    @Scheduled(cron = "0 0 0 * * *")
    public void runStatisticsJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobOperator.start(statisticsJob, params);
    }
}