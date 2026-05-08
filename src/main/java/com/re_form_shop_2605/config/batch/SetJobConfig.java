package com.re_form_shop_2605.config.batch;

import com.re_form_shop_2605.entity.payment.PointWallet;
import com.re_form_shop_2605.entity.trade.Trade;
import com.re_form_shop_2605.repository.payment.PointHistoryRepository;
import com.re_form_shop_2605.repository.payment.PointWalletRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class SetJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final TradeRepository tradeRepository;
    private final PointWalletRepository pointWalletRepository;
    private final PointHistoryRepository pointHistoryRepository;

    private static final double COMMISSION_RATE = 0.05; // 수수료

    // 1. JOB
//    @Bean
//    public Job setJob() {
//
//    }
//
//    // 2. Step
//    @Bean
//    public Step setStep() {
//
//    }
//
//    // 3. ItemReader
//    // 1) 미정산 거래
//    @Bean
//    public ItemReader<Trade> setUnsettledTradeReqder() {
//
//    }
//
//    // 4. ItemProcessor
//    // 1) 수수료 계산
//    @Bean
//    public ItemProcessor<Trade, PointWallet> setCommissionProcessor() {
//
//    }
//
//    // 5. ItemWriter
//    // 1) 포인트 지급
//    @Bean
//    public ItemWriter<PointWallet> setProvidePointWriter() {
//
//    }
}