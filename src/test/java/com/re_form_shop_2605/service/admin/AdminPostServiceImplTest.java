package com.re_form_shop_2605.service.admin;

import com.re_form_shop_2605.dto.admin.AdminPostDetailDTO;
import com.re_form_shop_2605.dto.admin.AdminPostListDTO;
import com.re_form_shop_2605.dto.admin.AdminPostActionRequestDTO;
import com.re_form_shop_2605.dto.admin.PostAction;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import com.re_form_shop_2605.entity.Enum.MemberStatus;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.Enum.Role;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.trade.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 작성자: 민기
 * 작성일: 2026-05-12
 * 설명: 관리자 게시글 관리 서비스의 목록 조회와 상태 처리 동작을 검증하는 테스트
 */
@SpringBootTest
class AdminPostServiceImplTest {

    @Autowired
    private AdminPostService adminPostService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    /**
     * 작성자: 민기
     * 작성일: 2026-05-12
     * 설명: 관리자 게시글 목록 조회가 키워드와 상태 조건으로 필터링되는지 검증한다.
     */
    void readPostsFiltersByKeywordAndStatus() {
        Member seller = createSeller("admin_post_filter");
        createPost(seller, "active_post", PostStatus.ON_SALE);
        Post hiddenPost = createPost(seller, "hidden_post", PostStatus.HIDDEN);

        PageResponse<AdminPostListDTO> response = adminPostService.readPosts("hidden_post", PostStatus.HIDDEN, 0, 10);

        assertTrue(response.content().stream().anyMatch(post ->
                post.postId().equals(hiddenPost.getPostId())
                        && post.status() == PostStatus.HIDDEN
        ));
    }

    @Test
    /**
     * 작성자: 민기
     * 작성일: 2026-05-12
     * 설명: 관리자 게시글 상세 조회가 판매자 정보까지 반환하는지 검증한다.
     */
    void readPostReturnsDetail() {
        Member seller = createSeller("admin_post_detail");
        Post post = createPost(seller, "detail_post", PostStatus.ON_SALE);

        AdminPostDetailDTO response = adminPostService.readPost(post.getPostId());

        assertEquals(post.getPostId(), response.postId());
        assertEquals(post.getTitle(), response.title());
        assertEquals(seller.getEmail(), response.sellerEmail());
    }

    @Test
    /**
     * 작성자: 민기
     * 작성일: 2026-05-12
     * 설명: 숨김 처리 시 게시글 상태가 HIDDEN으로 변경되는지 검증한다.
     */
    void processPostHideChangesStatus() {
        Member seller = createSeller("admin_post_hide");
        Post post = createPost(seller, "hide_post", PostStatus.ON_SALE);

        AdminPostDetailDTO response = adminPostService.processPost(
                post.getPostId(),
                new AdminPostActionRequestDTO(PostAction.HIDE, "숨김 처리")
        );

        assertEquals(PostStatus.HIDDEN, response.status());
    }

    @Test
    /**
     * 작성자: 민기
     * 작성일: 2026-05-12
     * 설명: 삭제 처리 시 게시글 상태가 DELETED로 변경되는지 검증한다.
     */
    void processPostDeleteChangesStatus() {
        Member seller = createSeller("admin_post_delete");
        Post post = createPost(seller, "delete_post", PostStatus.ON_SALE);

        AdminPostDetailDTO response = adminPostService.processPost(
                post.getPostId(),
                new AdminPostActionRequestDTO(PostAction.DELETE, "삭제 처리")
        );

        assertEquals(PostStatus.DELETED, response.status());
    }

    /**
     * 작성자: 민기
     * 작성일: 2026-05-12
     * 설명: 관리자 게시글 관리 테스트에 사용할 판매자 회원을 생성한다.
     */
    private Member createSeller(String prefix) {
        long seed = System.nanoTime();
        return memberRepository.save(Member.builder()
                .email(prefix + "_" + seed + "@test.com")
                .password("1234")
                .nickname(prefix + "_" + seed)
                .mannerScore(BigDecimal.ZERO)
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .warningCount(0)
                .emailEvent(false)
                .build());
    }

    /**
     * 작성자: 민기
     * 작성일: 2026-05-12
     * 설명: 관리자 게시글 관리 테스트에 사용할 판매글 엔티티를 생성한다.
     */
    private Post createPost(Member seller, String titlePrefix, PostStatus status) {
        long seed = System.nanoTime();
        return postRepository.save(Post.builder()
                .sellerId(seller)
                .title(titlePrefix + "_" + seed)
                .content("관리자 게시글 테스트 본문")
                .sport(Sport.BASEBALL)
                .team("LG")
                .uniformName("유니폼")
                .grade(Grade.A)
                .size("100")
                .marking(false)
                .price(10000)
                .deliveryType(DeliveryType.DIRECT)
                .status(status)
                .viewCount(0)
                .wishCount(0)
                .build());
    }
}
