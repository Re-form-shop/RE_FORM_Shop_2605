package com.re_form_shop_2605.service.member;

import com.re_form_shop_2605.dto.member.OnboardingRequestDTO;
import com.re_form_shop_2605.dto.member.OnboardingResponseDTO;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.Role;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.repository.member.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명:
 */
@Log4j2
@SpringBootTest
@Transactional
class InterestSettingServiceImplTest {

    @Autowired
    private InterestSettingService interestSettingService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void saveOnboardingTest() {
        Member member = createMember("save_onboarding");
        OnboardingRequestDTO requestDTO = new OnboardingRequestDTO(
                Sport.BASEBALL,
                "삼성",
                List.of("유니폼", "홈")
        );

        interestSettingService.saveOnboarding(member.getMemberId(), requestDTO);
        OnboardingResponseDTO responseDTO = interestSettingService.readOnboarding(member.getMemberId());

        assertEquals(Sport.BASEBALL, responseDTO.sport());
        assertEquals("삼성", responseDTO.team());
        assertEquals(2, responseDTO.keywords().size());
    }

    @Test
    void readOnboardingTest() {
        Member member = createMember("read_onboarding");
        interestSettingService.saveOnboarding(
                member.getMemberId(),
                new OnboardingRequestDTO(Sport.SOCCER, "FC 대구", List.of("원정", "상의"))
        );

        OnboardingResponseDTO responseDTO = interestSettingService.readOnboarding(member.getMemberId());

        assertEquals(Sport.SOCCER, responseDTO.sport());
        assertEquals("FC 대구", responseDTO.team());
        assertEquals(2, responseDTO.keywords().size());
    }

    private Member createMember(String prefix) {

        Member member = Member.builder()
                .email(prefix + "@test.com")
                .password("1234")
                .nickname(prefix)
                .mannerScore(BigDecimal.ZERO)
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .warningCount(0)
                .emailEvent(true)
                .build();
        return memberRepository.save(member);
    }
}
