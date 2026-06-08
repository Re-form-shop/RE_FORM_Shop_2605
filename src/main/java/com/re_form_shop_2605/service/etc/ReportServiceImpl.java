package com.re_form_shop_2605.service.etc;

import com.re_form_shop_2605.dto.admin.AdminReportDetailDTO;
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
 * 작성일: 2026-05-24
 * 설명: 신고 등록/조회와 관리자 신고 처리 기능을 제공하는 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService{
    // 경고 누적 시 정지 처리 기준
    private static final int SUSPEND_WARNING_THRESHOLD = 3;

    // 신고 저장소
    private final ReportRepository reportRepository;
    // 회원 저장소
    private final MemberRepository memberRepository;
    // 판매글 저장소
    private final PostRepository postRepository;
    // 커뮤니티 게시글 저장소
    private final CommunityPostRepository communityPostRepository;
    // 기존 프로젝트 공통 매퍼
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
    public AdminReportDetailDTO readAdminReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("신고가 존재하지 않습니다."));

        Member targetOwner = resolveTargetAuthor(report);

        return new AdminReportDetailDTO(
                report.getReportId(),
                report.getTargetType(),
                report.getTargetId(),
                report.getReason(),
                report.getDetail(),
                report.getStatus(),
                report.getCreatedAt(),
                report.getMember().getMemberId(),
                report.getMember().getNickname(),
                report.getMember().getEmail(),
                targetOwner.getMemberId(),
                targetOwner.getNickname(),
                targetOwner.getEmail(),
                resolveTargetTitle(report),
                resolveTargetSnapshot(report),
                report.getProcessedAt(),
                report.getProcessedBy(),
                report.getAdminMemo()
        );
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
    public AdminReportDetailDTO processReport(Long reportId, ReportStatus action, String adminMemo, String processedBy) {
        if (action == null || action == ReportStatus.PENDING) {
            throw new IllegalArgumentException("신고 처리 상태는 NORMAL, WARNING, DELETED 중 하나여야 합니다.");
        }

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("신고가 존재하지 않습니다."));
        if (report.getStatus() != ReportStatus.PENDING) {
            throw new IllegalArgumentException("이미 처리된 신고는 다시 처리할 수 없습니다.");
        }

        switch (action) {
            case NORMAL -> report.normal(adminMemo, processedBy);
            case WARNING -> {
                report.warn(adminMemo, processedBy);
                applyWarningToTargetAuthor(report);
            }
            case DELETED -> {
                report.delete(adminMemo, processedBy);
                deleteTargetContent(report);
            }
            default -> throw new IllegalArgumentException("지원하지 않는 신고 처리 상태입니다.");
        }

        return readAdminReport(reportId);
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

    private String resolveTargetTitle(Report report) {
        return switch (report.getTargetType()) {
            case POST -> postRepository.findById(report.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("신고 대상 판매글이 존재하지 않습니다."))
                    .getTitle();
            case COMMUNITY_POST -> communityPostRepository.findById(report.getTargetId())
                    .orElseThrow(() -> new IllegalArgumentException("신고 대상 커뮤니티 게시글이 존재하지 않습니다."))
                    .getCommTitle();
        };
    }

    private String resolveTargetSnapshot(Report report) {
        return switch (report.getTargetType()) {
            case POST -> {
                Post post = postRepository.findById(report.getTargetId())
                        .orElseThrow(() -> new IllegalArgumentException("신고 대상 판매글이 존재하지 않습니다."));
                yield post.getTitle();
            }
            case COMMUNITY_POST -> {
                CommunityPost communityPost = communityPostRepository.findById(report.getTargetId())
                        .orElseThrow(() -> new IllegalArgumentException("신고 대상 커뮤니티 게시글이 존재하지 않습니다."));
                yield communityPost.getCommTitle();
            }
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
