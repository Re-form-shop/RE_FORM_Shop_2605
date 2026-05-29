package com.re_form_shop_2605.service.admin;

import com.re_form_shop_2605.dto.admin.AdminMemberDetailDTO;
import com.re_form_shop_2605.dto.admin.AdminMemberListDTO;
import com.re_form_shop_2605.dto.admin.AdminMemberActionRequestDTO;
import com.re_form_shop_2605.dto.admin.MemberAction;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.Role;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 작성자: 김민기
 * 작성일: 2026-05-12
 * 설명: 관리자 회원 관리 서비스의 목록 조회와 제재 처리 동작을 검증하는 테스트
 */
@SpringBootTest
class AdminMemberServiceImplTest {

    @Autowired
    private AdminMemberService adminMemberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    /**
     * 작성자: 김민기
     * 작성일: 2026-05-12
     * 설명: 관리자 회원 목록 조회가 키워드와 상태 조건으로 필터링되는지 검증한다.
     */
    void readMembersFiltersByKeywordAndStatus() {
        createMember("admin_member_active", MemberStatus.ACTIVE);
        Member suspendedMember = createMember("admin_member_suspended", MemberStatus.SUSPENDED);

        PageResponse<AdminMemberListDTO> response = adminMemberService.readMembers("suspended", MemberStatus.SUSPENDED, 0, 10);

        assertTrue(response.content().stream().anyMatch(member ->
                member.memberId().equals(suspendedMember.getMemberId())
                        && member.status() == MemberStatus.SUSPENDED
        ));
    }

    @Test
    /**
     * 작성자: 김민기
     * 작성일: 2026-05-12
     * 설명: 관리자 회원 상세 조회가 기본 회원 정보를 반환하는지 검증한다.
     */
    void readMemberReturnsDetail() {
        Member member = createMember("admin_member_detail", MemberStatus.ACTIVE);

        AdminMemberDetailDTO response = adminMemberService.readMember(member.getMemberId());

        assertEquals(member.getMemberId(), response.memberId());
        assertEquals(member.getEmail(), response.email());
    }

    @Test
    /**
     * 작성자: 김민기
     * 작성일: 2026-05-12
     * 설명: 경고 처리 시 경고 횟수가 1 증가하는지 검증한다.
     */
    void processMemberWarnIncreasesWarningCount() {
        Member member = createMember("admin_member_warn", MemberStatus.ACTIVE);

        AdminMemberDetailDTO response = adminMemberService.processMember(
                member.getMemberId(),
                new AdminMemberActionRequestDTO(MemberAction.WARN, "경고 테스트")
        );

        assertEquals(1, response.warningCount());
        assertEquals(1, memberRepository.findById(member.getMemberId()).orElseThrow().getWarningCount());
    }

    @Test
    /**
     * 작성자: 김민기
     * 작성일: 2026-05-12
     * 설명: 정지 처리 시 회원 상태가 SUSPENDED로 변경되는지 검증한다.
     */
    void processMemberSuspendChangesStatus() {
        Member member = createMember("admin_member_suspend", MemberStatus.ACTIVE);

        AdminMemberDetailDTO response = adminMemberService.processMember(
                member.getMemberId(),
                new AdminMemberActionRequestDTO(MemberAction.SUSPEND, "정지 테스트")
        );

        assertEquals(MemberStatus.SUSPENDED, response.status());
    }

    @Test
    /**
     * 작성자: 김민기
     * 작성일: 2026-05-12
     * 설명: 탈퇴 처리 시 회원 상태가 WITHDRAWN으로 변경되는지 검증한다.
     */
    void processMemberWithdrawChangesStatus() {
        Member member = createMember("admin_member_withdraw", MemberStatus.ACTIVE);

        AdminMemberDetailDTO response = adminMemberService.processMember(
                member.getMemberId(),
                new AdminMemberActionRequestDTO(MemberAction.WITHDRAW, "탈퇴 테스트")
        );

        assertEquals(MemberStatus.WITHDRAWN, response.status());
    }

    /**
     * 작성자: 김민기
     * 작성일: 2026-05-12
     * 설명: 관리자 회원 관리 테스트에 사용할 회원 엔티티를 생성한다.
     */
    private Member createMember(String prefix, MemberStatus status) {
        long seed = System.nanoTime();
        return memberRepository.save(Member.builder()
                .email(prefix + "_" + seed + "@test.com")
                .password("1234")
                .nickname(prefix + "_" + seed)
                .mannerScore(BigDecimal.ZERO)
                .role(Role.USER)
                .status(status)
                .warningCount(0)
                .emailEvent(false)
                .build());
    }
}
