package com.re_form_shop_2605.service.etc;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.etc.ReportRequestDTO;
import com.re_form_shop_2605.dto.etc.ReportResponseDTO;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.entity.community.CommunityPost;
import com.re_form_shop_2605.entity.etc.Report;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.repository.community.CommunityPostRepository;
import com.re_form_shop_2605.repository.etc.ReportRepository;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.trade.PostRepository;
import com.re_form_shop_2605.service.common.ServicePageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 신고 기능을 제공하는 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService{
    private static final int SUSPEND_WARNING_THRESHOLD = 3;

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommunityPostRepository communityPostRepository;
    private final ModelMapper modelMapper;

    @Override
    // 신고자 정보를 확인한 뒤 신고를 저장
    public Long addReport(Long reporterId, ReportRequestDTO reportRequestDTO) {
        Member reporter = memberRepository.findById(reporterId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Report report = new Report(
                reporter,
                reportRequestDTO.targetType(),
                reportRequestDTO.targetId(),
                reportRequestDTO.reason(),
                reportRequestDTO.detail()
        );

        return reportRepository.save(report).getReportId();
    }

    @Override
    // 단건 신고를 조회해 응답 DTO로 반환
    public ReportResponseDTO readReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("신고가 존재하지 않습니다."));
        return toReportResponseDTO(report);
    }

    @Override
    // 신고자 기준 신고 내역을 페이지 단위로 반환
    public PageResponse<ReportResponseDTO> readReports(Long reporterId, int page, int size) {
        List<Report> reports = reportRepository.findAllByMember_MemberIdOrderByReportIdDesc(reporterId);
        List<ReportResponseDTO> reportResponseDTOList = new ArrayList<>();

        for (Report report : reports) {
            reportResponseDTOList.add(toReportResponseDTO(report));
        }

        return ServicePageResponse.of(reportResponseDTOList, page, size);
    }

    @Override
    public PageResponse<ReportResponseDTO> readAllReports(ReportStatus status, int page, int size) {
        List<Report> reports = status == null
                ? reportRepository.findAllByOrderByReportIdDesc()
                : reportRepository.findAllByStatusOrderByReportIdDesc(status);
        List<ReportResponseDTO> reportResponseDTOList = new ArrayList<>();

        for (Report report : reports) {
            reportResponseDTOList.add(toReportResponseDTO(report));
        }

        return ServicePageResponse.of(reportResponseDTOList, page, size);
    }

    @Override
    public ReportResponseDTO processReport(Long reportId, ReportStatus action) {
        if (action == null || action == ReportStatus.PENDING) {
            throw new IllegalArgumentException("신고 처리 상태는 NORMAL, WARNING, DELETED 중 하나여야 합니다.");
        }

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("신고가 존재하지 않습니다."));
        if (report.getStatus() != ReportStatus.PENDING) {
            throw new IllegalArgumentException("이미 처리된 신고는 다시 처리할 수 없습니다.");
        }

        switch (action) {
            case NORMAL -> report.normal();
            case WARNING -> {
                report.warn();
                applyWarningToTargetAuthor(report);
            }
            case DELETED -> {
                report.delete();
                deleteTargetContent(report);
            }
            default -> throw new IllegalArgumentException("지원하지 않는 신고 처리 상태입니다.");
        }

        return toReportResponseDTO(report);
    }

    private void applyWarningToTargetAuthor(Report report) {
        Member targetAuthor = resolveTargetAuthor(report);
        int nextWarningCount = targetAuthor.getWarningCount() + 1;
        targetAuthor.setWarningCount(nextWarningCount);

        if (nextWarningCount >= SUSPEND_WARNING_THRESHOLD && targetAuthor.getStatus() == MemberStatus.ACTIVE) {
            targetAuthor.setStatus(MemberStatus.SUSPENDED);
        }
    }

    private void deleteTargetContent(Report report) {
        switch (report.getTargetType()) {
            case POST -> {
                Post post = postRepository.findById(report.getTargetId())
                        .orElseThrow(() -> new IllegalArgumentException("신고 대상 판매글이 존재하지 않습니다."));
                post.changeStatus(PostStatus.DELETED);
            }
            case COMMUNITY_POST -> {
                CommunityPost communityPost = communityPostRepository.findById(report.getTargetId())
                        .orElseThrow(() -> new IllegalArgumentException("신고 대상 커뮤니티 게시글이 존재하지 않습니다."));
                communityPost.markDeleted();
            }
            default -> throw new IllegalArgumentException("지원하지 않는 신고 대상 타입입니다.");
        }
    }

    private Member resolveTargetAuthor(Report report) {
        return switch (report.getTargetType()) {
            case POST -> postRepository.findById(report.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("신고 대상 판매글이 존재하지 않습니다."))
                    .getSellerId();
            case COMMUNITY_POST -> communityPostRepository.findById(report.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("신고 대상 커뮤니티 게시글이 존재하지 않습니다."))
                    .getMember();
        };
    }

    // 신고 엔티티를 화면용 응답 DTO로 변환
    private ReportResponseDTO toReportResponseDTO(Report report) {
        return new ReportResponseDTO(
                report.getReportId(),
                report.getTargetType(),
                report.getTargetId(),
                report.getReason(),
                report.getDetail(),
                report.getStatus(),
                report.getCreatedAt()
        );
    }
}
