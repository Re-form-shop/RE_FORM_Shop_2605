package com.re_form_shop_2605.config.batch.payment;

import com.re_form_shop_2605.dto.batch.SettlementResult;
import com.re_form_shop_2605.entity.Enum.PointHistoryType;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.entity.payment.PointHistory;
import com.re_form_shop_2605.entity.payment.PointWallet;
import com.re_form_shop_2605.entity.trade.Trade;
import com.re_form_shop_2605.repository.payment.PointHistoryRepository;
import com.re_form_shop_2605.repository.payment.PointWalletRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SettlementJobConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final TradeRepository tradeRepository;
    private final PointWalletRepository pointWalletRepository;
    private final PointHistoryRepository pointHistoryRepository;

    private static final double COMMISSION_RATE = 0.05; // 수수료

    // 1. JOB
    @Bean
    public Job setJob() {
        return new JobBuilder("setJob", jobRepository)
                .start(setStep())
                .build();
    }

    // 2. Step
    @Bean
    public Step setStep() {
        return new StepBuilder("setStep", jobRepository)
                .<Trade, SettlementResult>chunk(10, platformTransactionManager)
                .reader(setUnsettledTradeReader())
                .processor(setCommissionProcessor())
                .writer(setProvidePointWriter())
                .build();
    }

    // 3. ItemReader
    // 1) 미정산 거래 조회 : status.trade가 CONFIRMED인데 point_history 테이블에 해당 trade_id가 없을 때
    @Bean
    public ItemReader<Trade> setUnsettledTradeReader() {
        List<Trade> trades = tradeRepository.findConfirmedUnsettledTrades(TradeStatus.CONFIRMED);
        return new ListItemReader<>(trades);
    }

    // 4. ItemProcessor
    // 1) 수수료 계산
    @Bean
    public ItemProcessor<Trade, SettlementResult> setCommissionProcessor() {
        return trade -> {
            // (1) trade에서 판매자 조회
            Long sellerId = trade.getSeller().getMemberId();

            // (2) 판매자 PointWallet 조회
            PointWallet wallet = pointWalletRepository.findByMemberMemberId(sellerId)
                    .orElseThrow(() -> new IllegalArgumentException("setCommissionProcessor : 해당 아이디의 포인트 지갑이 없습니다."));

            // (3) 수수료 계산 (trade_price * 0.05)
            int commission = (int) (trade.getTradePrice() * COMMISSION_RATE);

            // (4) 지급 포인트 계산 (trade_price - 수수료)
            int point = trade.getTradePrice() - commission;

            // (5) PointWallet 반환 (Writer에서 포인트 지급할 거라)
            wallet.earnPoint(point);
            return new SettlementResult(wallet, trade, point);
        };
    }

    // 5. ItemWriter
    // 1) 포인트 지급
    @Bean
    public ItemWriter<SettlementResult> setProvidePointWriter() {
        return chunk -> {
            for (SettlementResult result : chunk.getItems()) {
                // (1) PointWallet 저장
                pointWalletRepository.save(result.wallet());

                // (2) PointHistory 저장
                pointHistoryRepository.save(PointHistory.builder()
                        .pointWallet(result.wallet())
                        .trade(result.trade())
                        .type(PointHistoryType.EARN)
                        .changeAmount(result.point())
                        .balance(result.wallet().getBalance())
                        .build());

                // (3) Trade 상태 COMPLETE로 변경
                result.trade().changeStatus(TradeStatus.COMPLETED);
            }
        };
    }
}