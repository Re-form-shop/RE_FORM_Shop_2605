package com.re_form_shop_2605.config.batch;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameter;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@SpringBootTest
class BatchSchedulerTest {
    @Autowired
    private JobOperator jobOperator;

    @Autowired
    private Job dailyBatchJob;

    @Test
    void testDailyBatchJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addString("run.time", LocalDateTime.now().toString())
                .toJobParameters();

        JobExecution execution = jobOperator.run(dailyBatchJob, params);
        log.info("배치 실행 상태: {}", execution.getStatus());
    }
}