package com.re_form_shop_2605.service.member;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.member.MemberPublicDTO;
import com.re_form_shop_2605.dto.member.MemberRequestDTO;
import com.re_form_shop_2605.dto.member.MemberResponseDTO;
import com.re_form_shop_2605.dto.member.NicknameResponseDTO;
import com.re_form_shop_2605.dto.member.ProfileResponseDTO;
import com.re_form_shop_2605.dto.member.ProfileUpdateRequestDTO;
import com.re_form_shop_2605.entity.Enum.*;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.trade.MannerReview;
import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.entity.trade.Trade;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.trade.PostRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import com.re_form_shop_2605.repository.trade.mannerReviewRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = "spring.servlet.multipart.location=build/test-uploads")
@Log4j2
//@Transactional
class MemberServiceImplTest {
    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private TradeRepository tradeRepository;
    @Autowired private mannerReviewRepository mannerReviewRepository;
    @Autowired private MemberImageService memberImageService;

    @Test
    void joinTest() {
        long seed = System.nanoTime();
        MemberRequestDTO memberRequestDTO = new MemberRequestDTO(
                "join_" + seed + "@test.com",
                "join_" + seed,
                "password123",
                true
        );

        MemberResponseDTO responseDTO = memberService.join(memberRequestDTO);

        assertNotNull(responseDTO.user());
        assertNotNull(responseDTO.user().id());
        assertEquals(memberRequestDTO.email(), responseDTO.user().email());
    }

    @Test
    void isEmailDuplicateTest() {
        Member member = createMember("duplicate_email");
        assertTrue(memberService.isEmailDuplicate(member.getEmail()));
    }

    @Test
    void checkNicknameTest() {
        Member member = createMember("duplicate_nickname");
        NicknameResponseDTO responseDTO = memberService.checkNickname(member.getNickname());
        assertFalse(responseDTO.available());
    }

    @Test
    void readProfileTest() {
        Member member = createMember("read_profile");
        ProfileResponseDTO responseDTO = memberService.readProfile(member.getMemberId());

        assertEquals(member.getEmail(), responseDTO.email());
        assertEquals(member.getNickname(), responseDTO.nickname());
    }

    @Test
    void readEmailTest() {
        Member member = createMember("read_email");
        ProfileResponseDTO responseDTO = memberService.readEmail(member.getEmail());

        assertEquals(member.getMemberId(), responseDTO.memberId());
    }

    @Test
    void readNicknameTest() {
        Member member = createMember("read_nickname");
        ProfileResponseDTO responseDTO = memberService.readNickname(member.getNickname());

        assertEquals(member.getEmail(), responseDTO.email());
    }

    @Test
    void readAllProfilesTest() {
        String prefix = "all_profiles_" + System.nanoTime() + "_";
        for (int i = 0; i < 10; i++) {
            createMember(prefix + i);
        }

        PageResponse<ProfileResponseDTO> profiles = memberService.readAllProfiles(0, 10000);
        int matchedCount = 0;

        for (ProfileResponseDTO profile : profiles.content()) {
            if (profile.email().contains(prefix)) {
                matchedCount++;
            }
        }

        assertEquals(10, matchedCount);
    }

    @Test
    void modifyProfileTest() {
        Member member = createMember("modify_profile");
        long seed = System.nanoTime();
        ProfileUpdateRequestDTO requestDTO = new ProfileUpdateRequestDTO(
                "updated_profile_" + seed,
                null,
                "updated bio"
        );

        memberService.modifyProfile(member.getMemberId(), requestDTO);
        ProfileResponseDTO responseDTO = memberService.readProfile(member.getMemberId());

        assertEquals("updated_profile_" + seed, responseDTO.nickname());
        assertEquals(null, responseDTO.profileImageUrl());
        assertEquals("updated bio", responseDTO.bio());
    }

    @Test
    void modifyProfileWithProfileImageTest() throws Exception {
        Member member = createMember("modify_profile_image");
        long seed = System.nanoTime();
        MockMultipartFile profileImage = new MockMultipartFile(
                "profileImage",
                "profile-test.png",
                "image/png",
                "fake-image-content".getBytes()
        );
        String temporaryProfileImageUrl = memberImageService.saveTemporaryProfileImage(member.getMemberId(), profileImage);
        ProfileUpdateRequestDTO requestDTO = new ProfileUpdateRequestDTO(
                "updated_image_profile_" + seed,
                temporaryProfileImageUrl,
                "updated image bio"
        );

        try {
            memberService.modifyProfile(member.getMemberId(), requestDTO);
            ProfileResponseDTO responseDTO = memberService.readProfile(member.getMemberId());

            assertEquals("updated_image_profile_" + seed, responseDTO.nickname());
            assertEquals("updated image bio", responseDTO.bio());
            assertNotNull(responseDTO.profileImageUrl());
            assertTrue(responseDTO.profileImageUrl().startsWith("/uploads/member/" + member.getMemberId() + "/profile_"));

            String relativePath = responseDTO.profileImageUrl().replace("/uploads/", "");
            Path savedPath = Paths.get("build/test-uploads").toAbsolutePath().resolve(relativePath);
            assertTrue(Files.exists(savedPath));
            assertFalse(Files.exists(Paths.get("build/test-uploads/member/temp/" + member.getMemberId())));
        } finally {
            memberImageService.deleteProfileImageDirectory(member.getMemberId());
        }
    }

    @Test
    void readPublicProfileTest() {
        Member seller = createMember("public_seller");
        Member buyer = createMember("public_buyer");
        Trade trade = createCompletedTrade(seller, buyer);
        createReview(trade, buyer, seller, "좋아요");

        MemberPublicDTO responseDTO = memberService.readPublicProfile(seller.getMemberId());

        assertEquals(seller.getMemberId(), responseDTO.memberId());
        assertEquals(1, responseDTO.totalSales());
        assertEquals(1, responseDTO.recentReviews().size());
    }

    @Test
    void modifyStatusTest() {
        Member member = createMember("modify_status");
        memberService.modifyStatus(member.getMemberId(), MemberStatus.SUSPENDED);

        assertEquals(MemberStatus.SUSPENDED, memberRepository.findById(member.getMemberId()).orElseThrow().getStatus());
    }

    @Test
    void modifyWarningCountTest() {
        Member member = createMember("modify_warning");
        memberService.modifyWarningCount(member.getMemberId(), 3);

        assertEquals(3, memberRepository.findById(member.getMemberId()).orElseThrow().getWarningCount());
    }

    @Test
    void removeTest() {
        Member member = createMember("remove_member");
        memberService.remove(member.getMemberId());

        assertEquals(MemberStatus.WITHDRAWN, memberRepository.findById(member.getMemberId()).orElseThrow().getStatus());
    }

    private Member createMember(String prefix) {
        long seed = System.nanoTime();
        Member member = Member.builder()
                .email(prefix + "_" + seed + "@test.com")
                .password("password123")
                .nickname(prefix + "_" + seed)
                .mannerScore(BigDecimal.ZERO)
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .warningCount(0)
                .emailEvent(true)
                .build();
        return memberRepository.save(member);
    }

    private Trade createCompletedTrade(Member seller, Member buyer) {
        Post post = Post.builder()
                .sellerId(seller)
                .title("trade post")
                .content("trade content")
                .sport(Sport.BASEBALL)
                .team("LG")
                .uniformName("uniform")
                .grade(Grade.A)
                .size("100")
                .marking(false)
                .price(10000)
                .deliveryType(DeliveryType.DIRECT)
                .status(PostStatus.ON_SALE)
                .viewCount(0)
                .wishCount(0)
                .build();
        post = postRepository.save(post);

        Trade trade = Trade.builder()
                .post(post)
                .buyer(buyer)
                .seller(seller)
                .status(TradeStatus.COMPLETED)
                .deliveryType(TradeDeliveryType.DIRECT)
                .tradePrice(10000)
                .build();
        return tradeRepository.save(trade);
    }

    private void createReview(Trade trade, Member buyer, Member seller, String content) {
        MannerReview review = MannerReview.builder()
                .trade(trade)
                .buyer(buyer)
                .seller(seller)
                .score(5)
                .content(content)
                .build();
        mannerReviewRepository.save(review);
    }
}
