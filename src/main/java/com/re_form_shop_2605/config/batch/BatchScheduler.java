package com.re_form_shop_2605.config.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchScheduler {
    private final JobOperator jobOperator;
    private final Job setJob; // SettlementJob (정산)

    /* 구매자의 '구매 확정' 시, 판매자 point wallet으로 수수료 제외 포인트 지급 */
    @Scheduled(cron = "0 0 4 * * ?")
    public void runSettlementJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobOperator.start(setJob, params);
    }
}