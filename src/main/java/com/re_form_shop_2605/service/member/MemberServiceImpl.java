package com.re_form_shop_2605.service.member;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.login.AuthUserDTO;
import com.re_form_shop_2605.dto.member.MemberPublicDTO;
import com.re_form_shop_2605.dto.member.MemberRequestDTO;
import com.re_form_shop_2605.dto.member.MemberResponseDTO;
import com.re_form_shop_2605.dto.member.NicknameResponseDTO;
import com.re_form_shop_2605.dto.member.ProfileResponseDTO;
import com.re_form_shop_2605.dto.member.ProfileUpdateRequestDTO;
import com.re_form_shop_2605.dto.member.MemberBriefDTO;
import com.re_form_shop_2605.dto.trade.ReviewResponseDTO;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.Role;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.trade.MannerReview;
import com.re_form_shop_2605.entity.payment.PointWallet;
import com.re_form_shop_2605.repository.payment.PointWalletRepository;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import com.re_form_shop_2605.repository.trade.mannerReviewRepository;
import com.re_form_shop_2605.service.common.ServicePageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 회원 관련 비즈니스 로직을 처리하는 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TradeRepository tradeRepository;
    private final mannerReviewRepository mannerReviewRepository;
    private final InterestSettingService interestSettingService;
    private final MemberImageService memberImageService;
    private final PointWalletRepository pointWalletRepository; // 포인트 지갑 저장소
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    // 회원가입 요청을 검증하고 저장한 뒤 응답 DTO를 반환
    public MemberResponseDTO join(MemberRequestDTO memberRequestDTO) {
        if (memberRepository.existsByEmail(memberRequestDTO.email())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (memberRepository.existsByNickname(memberRequestDTO.nickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        Member member = Member.builder()
                .email(memberRequestDTO.email())
                .nickname(memberRequestDTO.nickname())
                .mannerScore(BigDecimal.ZERO)
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .warningCount(0)
                .emailEvent(false)
                .build();
        member.setPassword(passwordEncoder.encode(memberRequestDTO.password()));
        member.changeEmailEvent(memberRequestDTO.marketingAgreed());

        Member savedMember = memberRepository.save(member);

        // 포인트 지갑 자동 생성 (신규 가입자 대상)
        pointWalletRepository.save(PointWallet.builder().member(savedMember).build());

        return new MemberResponseDTO(null, null, toAuthUserDTO(savedMember));
    }

    @Override
    @Transactional(readOnly = true)
    // 이메일 중복 여부를 반환
    public boolean isEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    // 닉네임 사용 가능 여부를 응답 DTO로 반환
    public NicknameResponseDTO checkNickname(String nickname) {
        return new NicknameResponseDTO(!memberRepository.existsByNickname(nickname));
    }

    @Override
    @Transactional(readOnly = true)
    // 회원 번호 기준으로 내 프로필을 조회
    public ProfileResponseDTO readProfile(Long memberId) {
        return toProfileResponse(getMember(memberId));
    }

    @Override
    @Transactional(readOnly = true)
    // 이메일 기준으로 회원 프로필을 조회
    public ProfileResponseDTO readEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return toProfileResponse(member);
    }

    @Override
    @Transactional(readOnly = true)
    // 닉네임 기준으로 회원 프로필을 조회
    public ProfileResponseDTO readNickname(String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return toProfileResponse(member);
    }

    @Override
    @Transactional(readOnly = true)
    // 전체 회원 목록을 프로필 응답 형태로 변환해 페이지 단위로 반환
    public PageResponse<ProfileResponseDTO> readAllProfiles(int page, int size) {
        List<Member> members = memberRepository.findAll();
        List<ProfileResponseDTO> profileResponseDTOList = new ArrayList<>();

        for (Member member : members) {
            profileResponseDTOList.add(toProfileResponse(member));
        }

        return ServicePageResponse.of(profileResponseDTOList, page, size);
    }

    @Override
    // 내 프로필에서 수정 가능한 항목만 반영
    public void modifyProfile(Long memberId, ProfileUpdateRequestDTO profileUpdateRequestDTO) {
        Member member = getMember(memberId);

        String nickname = profileUpdateRequestDTO.nickname() != null
                ? profileUpdateRequestDTO.nickname()
                : member.getNickname();
        String bio = profileUpdateRequestDTO.bio() != null
                ? profileUpdateRequestDTO.bio()
                : member.getBio();
        String profileImageUrl = profileUpdateRequestDTO.profileImageUrl() != null
                ? profileUpdateRequestDTO.profileImageUrl()
                : member.getProfileImageUrl();

        if (!member.getNickname().equals(nickname) && memberRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        if (profileUpdateRequestDTO.profileImageUrl() != null) {
            memberImageService.deleteProfileImageDirectory(memberId);
            profileImageUrl = memberImageService.promoteTemporaryProfileImage(memberId, profileImageUrl);
        }

        member.changeProfile(
                nickname,
                profileImageUrl,
                bio
        );
    }

    @Override
    @Transactional(readOnly = true)
    // 공개 프로필 화면에 필요한 요약 정보와 최근 리뷰를 조립
    public MemberPublicDTO readPublicProfile(Long memberId) {
        Member member = getMember(memberId);
        int totalSales = tradeRepository.countBySeller_MemberIdAndStatus(memberId, TradeStatus.COMPLETED);

        List<MannerReview> reviews = mannerReviewRepository.findTop5BySeller_MemberIdOrderByMannerIdDesc(memberId);
        List<ReviewResponseDTO> recentReviews = new ArrayList<>();

        for (MannerReview review : reviews) {
            recentReviews.add(toReviewResponseDTO(review));
        }

        return new MemberPublicDTO(
                member.getMemberId(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getBio(),
                member.getMannerScore(),
                totalSales,
                recentReviews
        );
    }

    @Override
    // 관리자 기능에서 회원 상태를 변경
    public void modifyStatus(Long memberId, MemberStatus status) {
        getMember(memberId).setStatus(status);
    }

    @Override
    // 관리자 기능에서 경고 횟수를 변경
    public void modifyWarningCount(Long memberId, int warningCount) {
        getMember(memberId).setWarningCount(warningCount);
    }

    @Override
    // 회원 상태를 탈퇴 상태로 변경한다.
    public void remove(Long memberId) {
        getMember(memberId).setStatus(MemberStatus.WITHDRAWN);
    }

    // 공통으로 사용하는 회원 조회 메서드
    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }

    // 회원 엔티티를 인증 응답용 member DTO로 변환
    private AuthUserDTO toAuthUserDTO(Member member) {
        return new AuthUserDTO(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getRole(),
                member.getMannerScore()
        );
    }

    // 프로필 화면에 필요한 통계와 관심 설정을 함께 조립
    private ProfileResponseDTO toProfileResponse(Member member) {
        int totalSales = tradeRepository.countBySeller_MemberIdAndStatus(member.getMemberId(), TradeStatus.COMPLETED);
        int totalPurchases = tradeRepository.countByBuyer_MemberIdAndStatus(member.getMemberId(), TradeStatus.COMPLETED);

        return new ProfileResponseDTO(
                member.getMemberId(),
                member.getEmail(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getBio(),
                member.getMannerScore(),
                member.getRole(),
                member.getStatus(),
                0,
                0,
                0,
                totalSales,
                totalPurchases,
                interestSettingService.readOnboarding(member.getMemberId()),
                member.getCreatedAt()
        );
    }

    // 리뷰 엔티티를 화면용 응답 DTO로 변환
    private ReviewResponseDTO toReviewResponseDTO(MannerReview review) {
        MemberBriefDTO buyer = new MemberBriefDTO(
                review.getBuyer().getMemberId(),
                review.getBuyer().getNickname(),
                review.getBuyer().getProfileImageUrl()
        );
        MemberBriefDTO seller = new MemberBriefDTO(
                review.getSeller().getMemberId(),
                review.getSeller().getNickname(),
                review.getSeller().getProfileImageUrl()
        );

        return new ReviewResponseDTO(
                review.getMannerId(),
                review.getTrade().getTradeId(),
                buyer,
                seller,
                (int) Math.round(review.getScore()),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
