package com.re_form_shop_2605.service.admin;

import com.re_form_shop_2605.dto.admin.AdminDashboardSummaryDTO;
import com.re_form_shop_2605.dto.admin.AdminTradeSummaryDTO;
import com.re_form_shop_2605.entity.Enum.PointRequestStatus;
import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.entity.trade.Trade;
import com.re_form_shop_2605.repository.etc.ReportRepository;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.payment.PointRequestRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 설명: 현재 구현된 저장소를 조합해 관리자 대시보드 요약을 구성한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;
    private final PointRequestRepository pointRequestRepository;
    private final TradeRepository tradeRepository;

    @Override
    public AdminDashboardSummaryDTO readSummary() {
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime startOfTomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        List<Trade> recentTrades = tradeRepository.findTop5ByOrderByCreatedAtDesc();

        return new AdminDashboardSummaryDTO(
                memberRepository.count(),
                reportRepository.countByStatus(ReportStatus.PENDING),
                pointRequestRepository.countByStatus(PointRequestStatus.PENDING),
                tradeRepository.countByStatus(TradeStatus.DISPUTED),
                tradeRepository.countByCreatedAtGreaterThanEqualAndCreatedAtLessThan(startOfToday, startOfTomorrow),
                tradeRepository.countByCompletedAtGreaterThanEqualAndCompletedAtLessThan(startOfToday, startOfTomorrow),
                tradeRepository.countByStatusAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
                        TradeStatus.CANCELED,
                        startOfToday,
                        startOfTomorrow
                ),
                tradeRepository.sumTradePriceByCreatedAtBetween(startOfToday, startOfTomorrow),
                recentTrades.stream()
                        .map(this::toTradeSummaryDTO)
                        .toList()
        );
    }

    private AdminTradeSummaryDTO toTradeSummaryDTO(Trade trade) {
        return new AdminTradeSummaryDTO(
                trade.getTradeId(),
                trade.getPost().getPostId(),
                trade.getPost().getTitle(),
                null,
                trade.getTradePrice(),
                trade.getStatus(),
                trade.getCreatedAt(),
                trade.getConfirmedAt(),
                trade.getCompletedAt(),
                trade.getBuyer().getMemberId(),
                trade.getBuyer().getNickname(),
                trade.getSeller().getMemberId(),
                trade.getSeller().getNickname()
        );
    }
}
