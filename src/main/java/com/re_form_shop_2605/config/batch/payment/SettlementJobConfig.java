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

import java.time.LocalDateTime;
import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 손민정
 * 작성일: 2026-05-09
 * 설명: 정산 배치 Job 설정
 *       - dailyBatchJob: 매일 새벽 4시 실행
 *       - autoConfirmStep: RECEIVED 상태 + 5일 경과 거래 자동 구매 확정 (CONFIRMED)
 *       - setStep: CONFIRMED 상태 + 미정산 거래 판매자 포인트 정산 (수수료 5% 차감 후 지급)
 * ─────────────────────────────────────────────────────
 */

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
    // 1) 자동 구매 확정 -> 미정산 거래 정산 처리
    @Bean
    public Job dailyBatchJob() {
        return new JobBuilder("dailyBatchJob", jobRepository)
                .start(autoConfirmStep())
                .next(setStep())
                .build();
    }

    // 2. Step
    // 1) 미정산
    @Bean
    public Step setStep() {
        return new StepBuilder("setStep", jobRepository)
                .<Trade, SettlementResult>chunk(10)
                .transactionManager(platformTransactionManager)
                .reader(new ItemReader<>() {
                    private List<Trade> trades;
                    private int index = 0;

                    @Override
                    public Trade read() {
                        if (trades == null) {
                            trades = tradeRepository.findConfirmedUnsettledTrades(TradeStatus.CONFIRMED);
                        }
                        return index < trades.size() ? trades.get(index++) : null;
                    }
                })
                .processor(setCommissionProcessor())
                .writer(setProvidePointWriter())
                .build();
    }

    // 2) 자동 구매 확정
    @Bean
    public Step autoConfirmStep() {
        return new StepBuilder("autoConfirmStep", jobRepository)
                .<Trade, Trade>chunk(10)
                .transactionManager(platformTransactionManager)
                .reader(autoConfirmReader())
                .writer(autoConfirmWriter())
                .build();
    }

    // 3. ItemReader
    // 1) 미정산 거래 조회 : status.trade가 CONFIRMED인데 point_history 테이블에 해당 trade_id가 없을 때
    @Bean
    public ItemReader<Trade> setUnsettledTradeReader() {
        List<Trade> trades = tradeRepository.findConfirmedUnsettledTrades(TradeStatus.CONFIRMED);
        return new ListItemReader<>(trades);
    }

    // 2) 5일 이내 구매 확정 처리되지 않은 거래 조회 : status.trade가 RECEIVED + received_at.trade이 5일 경과
    @Bean
    public ItemReader<Trade> autoConfirmReader() {
        LocalDateTime dueDate = LocalDateTime.now().minusDays(5);
        List<Trade> trades = tradeRepository.findAutoConfirmTargets(TradeStatus.RECEIVED, dueDate);
        return new ListItemReader<>(trades);
    }

    // 4. ItemProcessor
    // 1) 미정산 - 수수료 계산
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
            wallet.confirm(trade.getTradePrice(), point);
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
                result.trade().complete();
                tradeRepository.save(result.trade());
            }
        };
    }

    // 2) 자동 구매 확정 상태 변경
    @Bean
    public ItemWriter<Trade> autoConfirmWriter() {
        return chunk -> {
            for (Trade trade : chunk.getItems()) {
                // (1) 상태 변경
                trade.confirm();
                tradeRepository.save(trade);
            }
        };
    }
}
