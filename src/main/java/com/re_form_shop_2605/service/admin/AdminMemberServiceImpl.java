package com.re_form_shop_2605.service.admin;

import com.re_form_shop_2605.dto.admin.AdminMemberDetailDTO;
import com.re_form_shop_2605.dto.admin.AdminMemberListDTO;
import com.re_form_shop_2605.dto.admin.AdminMemberActionRequestDTO;
import com.re_form_shop_2605.dto.admin.MemberAction;
import com.re_form_shop_2605.dto.admin.AdminTradeSummaryDTO;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.etc.ReportResponseDTO;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.entity.etc.Report;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.trade.Trade;
import com.re_form_shop_2605.repository.etc.ReportRepository;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import com.re_form_shop_2605.service.common.ServicePageResponse;
import com.re_form_shop_2605.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-24
 * 설명: 관리자 회원 조회와 제재 처리를 제공하는 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AdminMemberServiceImpl implements AdminMemberService {

    // 회원 저장소
    private final MemberRepository memberRepository;
    // 거래 저장소
    private final TradeRepository tradeRepository;
    // 신고 저장소
    private final ReportRepository reportRepository;
    // 회원 도메인 서비스
    private final MemberService memberService;

    @Override
    @Transactional(readOnly = true)
    // 키워드와 상태 조건으로 관리자용 회원 목록을 조회한다.
    public PageResponse<AdminMemberListDTO> readMembers(String keyword, MemberStatus status, int page, int size) {
        List<Member> members = memberRepository.findAllByOrderByMemberIdDesc();
        List<AdminMemberListDTO> filteredMembers = new ArrayList<>();

        for (Member member : members) {
            if (!matchesStatus(member, status) || !matchesKeyword(member, keyword)) {
                continue;
            }
            filteredMembers.add(toAdminMemberListDTO(member));
        }

        return ServicePageResponse.of(filteredMembers, page, size);
    }

    @Override
    @Transactional(readOnly = true)
    // 관리자용 회원 상세 정보를 조회한다.
    public AdminMemberDetailDTO readMember(Long memberId) {
        Member member = getMember(memberId);
        return toAdminMemberDetailDTO(member);
    }

    @Override
    // 관리자 액션에 따라 회원 경고, 정지, 탈퇴 처리를 수행한다.
    public AdminMemberDetailDTO processMember(Long memberId, AdminMemberActionRequestDTO requestDTO) {
        Member member = getMember(memberId);

        if (requestDTO.action() == MemberAction.WARN) {
            memberService.modifyWarningCount(memberId, member.getWarningCount() + 1);
        } else if (requestDTO.action() == MemberAction.SUSPEND) {
            memberService.modifyStatus(memberId, MemberStatus.SUSPENDED);
        } else if (requestDTO.action() == MemberAction.UNSUSPEND) {
            memberService.modifyStatus(memberId, MemberStatus.ACTIVE);
        } else if (requestDTO.action() == MemberAction.WITHDRAW) {
            memberService.remove(memberId);
        }

        return toAdminMemberDetailDTO(member);
    }

    // 회원 ID로 회원 엔티티를 조회한다.
    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }

    // 회원 상태 필터 조건과 일치하는지 확인한다.
    private boolean matchesStatus(Member member, MemberStatus status) {
        return status == null || member.getStatus() == status;
    }

    // 이메일 또는 닉네임이 검색 키워드와 일치하는지 확인한다.
    private boolean matchesKeyword(Member member, String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return true;
        }

        String normalizedKeyword = keyword.toLowerCase(Locale.ROOT);
        return member.getEmail().toLowerCase(Locale.ROOT).contains(normalizedKeyword)
                || member.getNickname().toLowerCase(Locale.ROOT).contains(normalizedKeyword);
    }

    // 회원 엔티티를 관리자 회원 목록 응답 DTO로 변환한다.
    private AdminMemberListDTO toAdminMemberListDTO(Member member) {
        return new AdminMemberListDTO(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                member.getStatus(),
                member.getWarningCount(),
                member.getMannerScore(),
                member.getCreatedAt()
        );
    }

    // 회원 엔티티를 관리자 회원 상세 응답 DTO로 변환한다.
    private AdminMemberDetailDTO toAdminMemberDetailDTO(Member member) {
        return new AdminMemberDetailDTO(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getBio(),
                member.getStatus(),
                member.getWarningCount(),
                member.getMannerScore(),
                member.getRole(),
                member.getCreatedAt(),
                null,
                readMemberReports(member.getMemberId()),
                tradeRepository.countBySeller_MemberIdAndStatus(member.getMemberId(), TradeStatus.COMPLETED),
                tradeRepository.countByBuyer_MemberIdAndStatus(member.getMemberId(), TradeStatus.COMPLETED),
                readRecentTrades(member.getMemberId())
        );
    }

    // 해당 회원이 등록한 신고 이력을 관리자 상세 응답용으로 변환한다.
    private List<ReportResponseDTO> readMemberReports(Long memberId) {
        List<Report> reports = reportRepository.findAllByMember_MemberIdOrderByReportIdDesc(memberId);
        List<ReportResponseDTO> reportResponseDTOList = new ArrayList<>();

        for (Report report : reports) {
            reportResponseDTOList.add(new ReportResponseDTO(
                    report.getReportId(),
                    report.getTargetType(),
                    report.getTargetId(),
                    report.getReason(),
                    report.getDetail(),
                    report.getStatus() == null ? ReportStatus.PENDING : report.getStatus(),
                    report.getCreatedAt()
            ));
        }

        return reportResponseDTOList;
    }

    private List<AdminTradeSummaryDTO> readRecentTrades(Long memberId) {
        return tradeRepository.findRecentTradesForAdminMember(memberId).stream()
                .limit(10)
                .map(trade -> new AdminTradeSummaryDTO(
                        trade.getTradeId(),
                        trade.getPost().getPostId(),
                        trade.getPost().getTitle(),
                        trade.getSeller().getMemberId().equals(memberId) ? "SELLER" : "BUYER",
                        trade.getTradePrice(),
                        trade.getStatus(),
                        trade.getCreatedAt(),
                        trade.getConfirmedAt(),
                        trade.getCompletedAt(),
                        trade.getBuyer().getMemberId(),
                        trade.getBuyer().getNickname(),
                        trade.getSeller().getMemberId(),
                        trade.getSeller().getNickname()
                ))
                .toList();
    }
}
