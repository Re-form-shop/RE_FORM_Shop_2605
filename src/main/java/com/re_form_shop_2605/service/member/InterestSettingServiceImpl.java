package com.re_form_shop_2605.service.member;

import com.re_form_shop_2605.dto.member.OnboardingRequestDTO;
import com.re_form_shop_2605.dto.member.OnboardingResponseDTO;
import com.re_form_shop_2605.entity.member.InterestKeyword;
import com.re_form_shop_2605.entity.member.InterestSetting;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.repository.member.InterestKeywordRepository;
import com.re_form_shop_2605.repository.member.InterestSettingRepository;
import com.re_form_shop_2605.repository.member.MemberRepository;
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
 * 설명: 회원 관심 설정 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class InterestSettingServiceImpl implements InterestSettingService{

    private final InterestSettingRepository interestSettingRepository;
    private final InterestKeywordRepository interestKeywordRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    // 종목, 팀, 키워드를 회원 관심 설정으로 저장
    public void saveOnboarding(Long memberId, OnboardingRequestDTO onboardingRequestDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        InterestSetting mappedInterestSetting = modelMapper.map(onboardingRequestDTO, InterestSetting.class);
        InterestSetting interestSetting = InterestSetting.builder()
                .member(member)
                .sport(mappedInterestSetting.getSport())
                .team(mappedInterestSetting.getTeam())
                .build();

        interestSettingRepository.save(interestSetting);
        interestKeywordRepository.deleteByMember_MemberId(memberId);

        List<String> keywords = onboardingRequestDTO.keywords();
        if (keywords == null) {
            keywords = List.of();
        }

        List<InterestKeyword> interestKeywords = new ArrayList<>();
        for (String keyword : keywords) {
            interestKeywords.add(InterestKeyword.builder()
                    .member(member)
                    .keyword(keyword)
                    .build());
        }

        interestKeywordRepository.saveAll(interestKeywords);
    }

    @Override
    @Transactional(readOnly = true)
    // 저장된 관심 설정과 키워드 목록을 읽어서 응답 DTO로 반환
    public OnboardingResponseDTO readOnboarding(Long memberId) {
        InterestSetting interestSetting = interestSettingRepository.findById(memberId).orElse(null);

        if (interestSetting == null) {
            return new OnboardingResponseDTO(null, null, List.of());
        }

        List<InterestKeyword> interestKeywordList = interestKeywordRepository.findAllByMember_MemberId(memberId);
        List<String> keywords = new ArrayList<>();

        for (InterestKeyword interestKeyword : interestKeywordList) {
            keywords.add(interestKeyword.getKeyword());
        }

        return new OnboardingResponseDTO(
                interestSetting.getSport(),
                interestSetting.getTeam(),
                keywords
        );
    }
}
