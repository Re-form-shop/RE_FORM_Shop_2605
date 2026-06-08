package com.re_form_shop_2605.service.admin;

import com.re_form_shop_2605.dto.admin.AdminDisputeAction;
import com.re_form_shop_2605.dto.admin.AdminDisputeDetailDTO;
import com.re_form_shop_2605.dto.admin.AdminDisputeListDTO;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.ReportTargetType;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.entity.etc.Report;
import com.re_form_shop_2605.entity.trade.Trade;
import com.re_form_shop_2605.repository.etc.ReportRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import com.re_form_shop_2605.service.common.ServicePageResponse;
import com.re_form_shop_2605.service.payment.PaymentService;
import com.re_form_shop_2605.service.trade.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-24
 * 설명: 기존 거래/신고 데이터를 조합하는 관리자 분쟁 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminDisputeServiceImpl implements AdminDisputeService {

    private final TradeRepository tradeRepository;
    private final ReportRepository reportRepository;
    private final PaymentService paymentService;
    private final TradeService tradeService;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<AdminDisputeListDTO> readDisputes(TradeStatus status, int page, int size) {
        TradeStatus targetStatus = status == null ? TradeStatus.DISPUTED : status;
        validateDisputeStatusFilter(targetStatus);

        List<AdminDisputeListDTO> items = tradeRepository.findAllByStatusOrderByCreatedAtDesc(targetStatus).stream()
                .map(this::toListDTO)
                .toList();

        return ServicePageResponse.of(items, page, size);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDisputeDetailDTO readDispute(Long tradeId) {
        return toDetailDTO(getTrade(tradeId));
    }

    @Override
    public AdminDisputeDetailDTO processDispute(Long tradeId, AdminDisputeAction action, String adminMemo, Integer extensionDays, String processedBy) {
        if (action == null) {
            throw new IllegalArgumentException("분쟁 처리 액션은 필수입니다.");
        }

        Trade trade = getTrade(tradeId);
        if (trade.getStatus() != TradeStatus.DISPUTED) {
            throw new IllegalArgumentException("분쟁 상태인 거래만 처리할 수 있습니다.");
        }

        switch (action) {
            case EXTEND -> {
                LocalDateTime extendedUntil = resolveExtendedUntil(extensionDays);
                trade.resolveDispute(TradeStatus.DISPUTED);
                recordDisputeReview(trade, adminMemo, processedBy, action, extendedUntil);
            }
            case REJECT -> {
                trade.resolveDispute(TradeStatus.CONFIRMED);
                trade.getPost().changeStatus(PostStatus.SOLD);
                recordDisputeReview(trade, adminMemo, processedBy, action, null);
            }
            case REFUND -> {
                paymentService.refundPaymentByTrade(tradeId, buildRefundReason(adminMemo));
                recordDisputeReview(trade, adminMemo, processedBy, action, null);
            }
            case COMPLETE -> {
                trade.resolveDispute(TradeStatus.CONFIRMED);
                tradeService.completeTradeByAdmin(tradeId);
                recordDisputeReview(trade, adminMemo, processedBy, action, null);
            }
            default -> throw new IllegalArgumentException("지원하지 않는 분쟁 처리 액션입니다.");
        }

        return toDetailDTO(trade);
    }

    private Trade getTrade(Long tradeId) {
        return tradeRepository.findWithPostAndBuyerAndSellerByTradeId(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("거래가 존재하지 않습니다."));
    }

    private AdminDisputeListDTO toListDTO(Trade trade) {
        return new AdminDisputeListDTO(
                trade.getTradeId(),
                trade.getPost().getPostId(),
                trade.getPost().getTitle(),
                trade.getTradePrice(),
                trade.getStatus(),
                trade.getDeliveryType(),
                trade.getCreatedAt(),
                trade.getBuyer().getMemberId(),
                trade.getBuyer().getNickname(),
                trade.getSeller().getMemberId(),
                trade.getSeller().getNickname()
        );
    }

    private AdminDisputeDetailDTO toDetailDTO(Trade trade) {
        DisputeReportSummary reportSummary = summarizeReports(trade);

        return new AdminDisputeDetailDTO(
                trade.getTradeId(),
                trade.getPost().getPostId(),
                trade.getPost().getTitle(),
                trade.getTradePrice(),
                trade.getStatus(),
                trade.getDeliveryType(),
                trade.getDeliveryAddress(),
                trade.getCourierCode(),
                trade.getCourierName(),
                trade.getTrackingNumber(),
                trade.getCreatedAt(),
                trade.getShippingStartedAt(),
                trade.getConfirmedAt(),
                trade.getCompletedAt(),
                reportSummary.disputedAt(),
                reportSummary.processedAt(),
                reportSummary.processedBy(),
                reportSummary.adminMemo(),
                reportSummary.resolutionType(),
                reportSummary.extendedUntil(),
                trade.getBuyer().getMemberId(),
                trade.getBuyer().getNickname(),
                trade.getBuyer().getEmail(),
                reportSummary.buyerClaim(),
                trade.getSeller().getMemberId(),
                trade.getSeller().getNickname(),
                trade.getSeller().getEmail(),
                reportSummary.sellerClaim()
        );
    }

    private void recordDisputeReview(Trade trade, String adminMemo, String processedBy, AdminDisputeAction action, LocalDateTime extendedUntil) {
        TradeReportBundle reportBundle = loadTradeReportBundle(trade);
        List<Report> buyerReports = reportBundle.buyerReports();

        if (buyerReports.isEmpty()) {
            return;
        }

        List<Report> pendingReports = buyerReports.stream()
                .filter(report -> report.getStatus() == com.re_form_shop_2605.entity.Enum.ReportStatus.PENDING)
                .toList();

        List<Report> targetReports = pendingReports.isEmpty()
                ? List.of(buyerReports.getFirst())
                : pendingReports;

        targetReports.forEach(report -> report.recordDisputeReview(
                adminMemo,
                processedBy,
                action.name(),
                extendedUntil
        ));
    }

    private DisputeReportSummary summarizeReports(Trade trade) {
        TradeReportBundle reportBundle = loadTradeReportBundle(trade);
        List<Report> buyerReports = reportBundle.buyerReports();
        List<Report> sellerReports = reportBundle.sellerReports();

        Optional<Report> latestBuyerReport = buyerReports.stream()
                .findFirst();
        Optional<Report> latestSellerReport = sellerReports.stream()
                .findFirst();
        Optional<Report> latestReviewedReport = buyerReports.stream()
                .filter(report -> report.getProcessedAt() != null)
                .max(Comparator.comparing(Report::getProcessedAt));

        return new DisputeReportSummary(
                latestBuyerReport.map(Report::getDetail).orElse(null),
                latestBuyerReport.map(Report::getSellerClaim)
                        .filter(claim -> claim != null && !claim.isBlank())
                        .or(() -> latestSellerReport.map(Report::getDetail))
                        .orElse(null),
                latestBuyerReport.map(Report::getCreatedAt).orElse(null),
                latestReviewedReport.map(Report::getProcessedAt).orElse(null),
                latestReviewedReport.map(Report::getProcessedBy).orElse(null),
                latestReviewedReport.map(Report::getAdminMemo).orElse(null),
                latestReviewedReport.map(Report::getDisputeResolutionType).orElse(null),
                latestReviewedReport.map(Report::getExtendedUntil).orElse(null)
        );
    }

    private TradeReportBundle loadTradeReportBundle(Trade trade) {
        List<Report> targetReports = reportRepository.findAllByTargetTypeAndTargetIdOrderByReportIdDesc(
                ReportTargetType.POST,
                trade.getPost().getPostId()
        );
        List<Report> buyerReports = new ArrayList<>();
        List<Report> sellerReports = new ArrayList<>();

        for (Report report : targetReports) {
            Long reporterId = report.getMember().getMemberId();
            if (reporterId.equals(trade.getBuyer().getMemberId())) {
                buyerReports.add(report);
            } else if (reporterId.equals(trade.getSeller().getMemberId())) {
                sellerReports.add(report);
            }
        }
        return new TradeReportBundle(buyerReports, sellerReports);
    }

    private LocalDateTime resolveExtendedUntil(Integer extensionDays) {
        if (extensionDays == null || extensionDays <= 0) {
            throw new IllegalArgumentException("기한 연장 처리에는 1일 이상의 extensionDays가 필요합니다.");
        }
        return LocalDateTime.now().plusDays(extensionDays);
    }

    private String buildRefundReason(String adminMemo) {
        if (adminMemo == null || adminMemo.isBlank()) {
            return "관리자 분쟁 환불 처리";
        }
        return adminMemo;
    }

    private void validateDisputeStatusFilter(TradeStatus status) {
        if (status != TradeStatus.DISPUTED
                && status != TradeStatus.CONFIRMED
                && status != TradeStatus.CANCELED
                && status != TradeStatus.COMPLETED) {
            throw new IllegalArgumentException("분쟁 목록은 DISPUTED, CONFIRMED, CANCELED, COMPLETED 상태만 조회할 수 있습니다.");
        }
    }

    private record DisputeReportSummary(
            String buyerClaim,
            String sellerClaim,
            java.time.LocalDateTime disputedAt,
            java.time.LocalDateTime processedAt,
            String processedBy,
            String adminMemo,
            String resolutionType,
            java.time.LocalDateTime extendedUntil
    ) {
    }

    private record TradeReportBundle(
            List<Report> buyerReports,
            List<Report> sellerReports
    ) {
    }
}
