package com.re_form_shop_2605.service.etc;

import com.re_form_shop_2605.dto.admin.AdminReportDetailDTO;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.etc.ReportRequestDTO;
import com.re_form_shop_2605.dto.etc.ReportResponseDTO;
import com.re_form_shop_2605.entity.Enum.CommunityPostStatus;
import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.ReportReason;
import com.re_form_shop_2605.entity.Enum.ReportStatus;
import com.re_form_shop_2605.entity.Enum.ReportTargetType;
import com.re_form_shop_2605.entity.Enum.Role;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.entity.community.CommunityPost;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.repository.community.CommunityPostRepository;
import com.re_form_shop_2605.repository.etc.ReportRepository;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.trade.PostRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Log4j2
@SpringBootTest(
        classes = ReportServiceImplTest.TestApplication.class,
        properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.url=jdbc:h2:mem:report-service-test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.sql.init.mode=never"
})
class ReportServiceImplTest {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommunityPostRepository communityPostRepository;

    @Autowired
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportRepository.deleteAll();
        communityPostRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();
        log.info("ReportServiceImplTest setup complete");
    }

    @Test
    void processPostWarningTest() {
        Member reporter = saveMember("reporter-warning");
        Member seller = saveMember("seller-warning");
        Post post = savePost(seller, "warning-post");

        Long reportId = reportService.addReport(
                reporter.getMemberId(),
                new ReportRequestDTO(ReportTargetType.POST, post.getPostId(), ReportReason.FRAUD, "사기 의심")
        );

        AdminReportDetailDTO responseDTO = reportService.processReport(
                reportId,
                ReportStatus.WARNING,
                "경고 처리",
                "admin"
        );
        Member updatedSeller = memberRepository.findById(seller.getMemberId()).orElseThrow();

        assertEquals(ReportStatus.WARNING, responseDTO.status());
        assertEquals(1, updatedSeller.getWarningCount());
        assertEquals(MemberStatus.ACTIVE, updatedSeller.getStatus());
    }

    @Test
    void processCommunityWarningSuspendsAtThirdStrikeTest() {
        Member reporter = saveMember("reporter-suspend");
        Member author = saveMember("author-suspend");
        author.setWarningCount(2);
        memberRepository.saveAndFlush(author);

        CommunityPost communityPost = saveCommunityPost(author, "suspend-community");

        Long reportId = reportService.addReport(
                reporter.getMemberId(),
                new ReportRequestDTO(ReportTargetType.COMMUNITY_POST, communityPost.getCommId(), ReportReason.INAPPROPRIATE, "부적절")
        );

        AdminReportDetailDTO responseDTO = reportService.processReport(
                reportId,
                ReportStatus.WARNING,
                "누적 경고",
                "admin"
        );
        Member updatedAuthor = memberRepository.findById(author.getMemberId()).orElseThrow();

        assertEquals(ReportStatus.WARNING, responseDTO.status());
        assertEquals(3, updatedAuthor.getWarningCount());
        assertEquals(MemberStatus.SUSPENDED, updatedAuthor.getStatus());
    }

    @Test
    void processPostDeletedMarksPostDeletedTest() {
        Member reporter = saveMember("reporter-delete-post");
        Member seller = saveMember("seller-delete-post");
        Post post = savePost(seller, "delete-post");

        Long reportId = reportService.addReport(
                reporter.getMemberId(),
                new ReportRequestDTO(ReportTargetType.POST, post.getPostId(), ReportReason.FAKE, "허위 매물")
        );

        AdminReportDetailDTO responseDTO = reportService.processReport(
                reportId,
                ReportStatus.DELETED,
                "게시글 삭제",
                "admin"
        );
        Post updatedPost = postRepository.findById(post.getPostId()).orElseThrow();

        assertEquals(ReportStatus.DELETED, responseDTO.status());
        assertEquals(com.re_form_shop_2605.entity.Enum.PostStatus.DELETED, updatedPost.getStatus());
    }

    @Test
    void processCommunityDeletedMarksPostDeletedTest() {
        Member reporter = saveMember("reporter-delete-community");
        Member author = saveMember("author-delete-community");
        CommunityPost communityPost = saveCommunityPost(author, "delete-community");

        Long reportId = reportService.addReport(
                reporter.getMemberId(),
                new ReportRequestDTO(ReportTargetType.COMMUNITY_POST, communityPost.getCommId(), ReportReason.ETC, "운영 정책 위반")
        );

        AdminReportDetailDTO responseDTO = reportService.processReport(
                reportId,
                ReportStatus.DELETED,
                "커뮤니티 글 삭제",
                "admin"
        );
        CommunityPost updatedPost = communityPostRepository.findById(communityPost.getCommId()).orElseThrow();

        assertEquals(ReportStatus.DELETED, responseDTO.status());
        assertEquals(CommunityPostStatus.DELETED, updatedPost.getStatus());
    }

    @Test
    void processReportRejectsAlreadyProcessedReportTest() {
        Member reporter = saveMember("reporter-duplicate");
        Member seller = saveMember("seller-duplicate");
        Post post = savePost(seller, "duplicate-post");

        Long reportId = reportService.addReport(
                reporter.getMemberId(),
                new ReportRequestDTO(ReportTargetType.POST, post.getPostId(), ReportReason.ETC, "중복 처리 방지")
        );
        reportService.processReport(reportId, ReportStatus.WARNING, "1차 처리", "admin");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> reportService.processReport(reportId, ReportStatus.DELETED, "재처리 시도", "admin")
        );

        assertEquals("이미 처리된 신고는 다시 처리할 수 없습니다.", ex.getMessage());
    }

    @Test
    void readAllReportsIncludesSavedDummyDataTest() {
        Member reporter = saveMember("reporter-read-all");
        Member seller = saveMember("seller-read-all");
        Post postA = savePost(seller, "read-all-a");
        Post postB = savePost(seller, "read-all-b");

        reportService.addReport(
                reporter.getMemberId(),
                new ReportRequestDTO(ReportTargetType.POST, postA.getPostId(), ReportReason.ETC, "detail-a")
        );
        reportService.addReport(
                reporter.getMemberId(),
                new ReportRequestDTO(ReportTargetType.POST, postB.getPostId(), ReportReason.FAKE, "detail-b")
        );

        PageResponse<ReportResponseDTO> reports = reportService.readAllReports(null, 0, 10);

        assertEquals(2, reports.content().size());
    }

    private Member saveMember(String prefix) {
        Member member = Member.builder()
                .email(prefix + "@test.com")
                .password("encoded-password")
                .nickname(prefix)
                .mannerScore(BigDecimal.ZERO)
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .warningCount(0)
                .emailEvent(true)
                .build();
        return memberRepository.saveAndFlush(member);
    }

    private Post savePost(Member seller, String prefix) {
        Post post = Post.builder()
                .sellerId(seller)
                .title(prefix + "-title")
                .content(prefix + "-content")
                .sport(Sport.BASEBALL)
                .team("KIA 타이거즈")
                .uniformName(prefix + "-uniform")
                .grade(Grade.A)
                .size("100")
                .marking(false)
                .price(10000)
                .deliveryType(DeliveryType.DELIVERY)
                .status(com.re_form_shop_2605.entity.Enum.PostStatus.ON_SALE)
                .viewCount(0)
                .wishCount(0)
                .build();
        return postRepository.saveAndFlush(post);
    }

    private CommunityPost saveCommunityPost(Member author, String prefix) {
        CommunityPost communityPost = CommunityPost.builder()
                .member(author)
                .sportCategory(Sport.BASEBALL)
                .teamCategory("KIA 타이거즈")
                .commTitle(prefix + "-title")
                .commContent(prefix + "-content")
                .commImageUrl(null)
                .commViewCount(0)
                .likeCount(0)
                .commentCount(0)
                .status(CommunityPostStatus.ACTIVE)
                .build();
        return communityPostRepository.saveAndFlush(communityPost);
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @EnableJpaAuditing
    @EntityScan(basePackages = "com.re_form_shop_2605.entity")
    @EnableJpaRepositories(basePackageClasses = {
            ReportRepository.class,
            MemberRepository.class,
            PostRepository.class,
            CommunityPostRepository.class
    })
    static class TestApplication {

        @Bean
        ModelMapper modelMapper() {
            return new ModelMapper();
        }

        @Bean
        ReportService reportService(
                ReportRepository reportRepository,
                MemberRepository memberRepository,
                PostRepository postRepository,
                CommunityPostRepository communityPostRepository,
                ModelMapper modelMapper
        ) {
            return new ReportServiceImpl(
                    reportRepository,
                    memberRepository,
                    postRepository,
                    communityPostRepository,
                    modelMapper
            );
        }
    }
}
