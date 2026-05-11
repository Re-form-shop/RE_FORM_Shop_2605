package com.re_form_shop_2605.service.trade;

import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.trade.PostCardDTO;
import com.re_form_shop_2605.dto.trade.PostDetailDTO;
import com.re_form_shop_2605.dto.trade.PostRequestDTO;
import com.re_form_shop_2605.dto.trade.PostUpdateRequestDTO;
import com.re_form_shop_2605.dto.trade.SellerBriefDTO;
import com.re_form_shop_2605.entity.Enum.PostStatus;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.entity.trade.PostImage;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.repository.trade.PostRepository;
import com.re_form_shop_2605.repository.trade.TradeRepository;
import com.re_form_shop_2605.repository.trade.WishRepository;
import com.re_form_shop_2605.repository.trade.postImageRepository;
import com.re_form_shop_2605.service.common.ServicePageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final postImageRepository postImageRepository;
    private final WishRepository wishRepository;
    private final PostImageService postImageService;
    private final ModelMapper modelMapper;

    @Override
    // 판매글을 저장하고, 함께 전달된 이미지를 이미지 폴더에 저장
    public Long addPost(Long sellerId, PostRequestDTO postRequestDTO, List<MultipartFile> images) {
        Member seller = memberRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Post post = modelMapper.map(postRequestDTO, Post.class);
        post = Post.builder()
                .sellerId(seller)
                .title(post.getTitle())
                .content(post.getContent())
                .sport(post.getSport())
                .team(post.getTeam())
                .uniformName(post.getUniformName())
                .grade(post.getGrade())
                .size(post.getSize())
                .marking(post.getMarking() != null ? post.getMarking() : Boolean.FALSE)
                .price(post.getPrice())
                .deliveryType(post.getDeliveryType())
                .status(PostStatus.ON_SALE)
                .viewCount(0)
                .wishCount(0)
                .build();

        Post savedPost = postRepository.save(post);
        List<String> imageUrls = postImageService.savePostImages(savedPost.getPostId(), images);

        List<PostImage> postImages = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            postImages.add(PostImage.builder()
                    .post(savedPost)
                    .imageUrl(imageUrls.get(i))
                    .sortOrder(i + 1)
                    .build());
        }

        postImageRepository.saveAll(postImages);
        return savedPost.getPostId();
    }

    @Override
    @Transactional(readOnly = true)
    // 판매글 상세 화면에 필요한 본문, 이미지, 찜 여부 반환
    public PostDetailDTO readPost(Long postId, Long viewerId) {
        Post post = getPost(postId);
        List<PostImage> postImages = postImageRepository.findAllByPost_PostIdOrderBySortOrderAsc(postId);
        List<String> imageUrls = new ArrayList<>();

        for (PostImage postImage : postImages) {
            imageUrls.add(postImage.getImageUrl());
        }

        boolean isWished = false;
        if (viewerId != null) {
            isWished = wishRepository.existsByMember_MemberIdAndPost_PostId(viewerId, postId);
        }

        SellerBriefDTO seller = toSellerBriefDTO(post.getSellerId());

        return new PostDetailDTO(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getSport(),
                post.getTeam(),
                post.getUniformName(),
                post.getGrade(),
                post.getSize(),
                post.getMarking(),
                post.getPrice(),
                post.getDeliveryType(),
                post.getStatus(),
                post.getRiskLevel(),
                post.getViewCount(),
                post.getWishCount(),
                isWished,
                imageUrls,
                post.getCreatedAt(),
                post.getUpdatedAt(),
                seller,
                post.getTrade() != null ? post.getTrade().getTradeId() : null
        );
    }

    @Override
    @Transactional(readOnly = true)
    // 노출 가능한 판매글 목록을 카드 DTO로 변환해 페이지 단위로 반환
    public PageResponse<PostCardDTO> readAllPosts(Long viewerId, int page, int size) {
        List<Post> posts = postRepository.findAllByStatusNotIn(List.of(PostStatus.HIDDEN, PostStatus.DELETED));
        List<PostCardDTO> postCardDTOList = new ArrayList<>();

        for (Post post : posts) {
            List<PostImage> postImages = postImageRepository.findAllByPost_PostIdOrderBySortOrderAsc(post.getPostId());
            String thumbnailUrl = null;
            if (!postImages.isEmpty()) {
                thumbnailUrl = postImages.get(0).getImageUrl();
            }

            boolean isWished = false;
            if (viewerId != null) {
                isWished = wishRepository.existsByMember_MemberIdAndPost_PostId(viewerId, post.getPostId());
            }

            SellerBriefDTO seller = toSellerBriefDTO(post.getSellerId());

            postCardDTOList.add(new PostCardDTO(
                    post.getPostId(),
                    post.getTitle(),
                    post.getTeam(),
                    post.getSport(),
                    post.getPrice(),
                    post.getGrade(),
                    post.getSize(),
                    post.getDeliveryType(),
                    post.getStatus(),
                    post.getViewCount(),
                    post.getWishCount(),
                    isWished,
                    thumbnailUrl,
                    toTimeAgo(post.getCreatedAt()),
                    post.getCreatedAt(),
                    seller
            ));
        }
        return ServicePageResponse.of(postCardDTOList, page, size);
    }

    @Override
    // 판매글과 이미지를 수정
    public void modifyPost(Long postId, Long sellerId, PostUpdateRequestDTO postUpdateRequestDTO, List<MultipartFile> images) {
        Post post = getPost(postId);

        if (!post.getSellerId().getMemberId().equals(sellerId)) {
            throw new IllegalArgumentException("판매글 수정 권한이 없습니다.");
        }
        post.changePost(
                postUpdateRequestDTO.title(),
                postUpdateRequestDTO.content(),
                postUpdateRequestDTO.grade(),
                postUpdateRequestDTO.size(),
                postUpdateRequestDTO.marking(),
                postUpdateRequestDTO.price(),
                postUpdateRequestDTO.deliveryType()
        );

        if (images != null) {
            postImageRepository.deleteByPost_PostId(postId);
            postImageService.deletePostImageDirectory(postId);

            List<String> imageUrls = postImageService.savePostImages(postId, images);
            List<PostImage> postImages = new ArrayList<>();

            for (int i = 0; i < imageUrls.size(); i++) {
                postImages.add(PostImage.builder()
                        .post(post)
                        .imageUrl(imageUrls.get(i))
                        .sortOrder(i + 1)
                        .build());
            }
            postImageRepository.saveAll(postImages);
        }
    }

    @Override
    // 판매글을 삭제 상태로 변경
    public void removePost(Long postId, Long sellerId) {
        Post post = getPost(postId);

        if (!post.getSellerId().getMemberId().equals(sellerId)) {
            throw new IllegalArgumentException("판매글 삭제 권한이 없습니다.");
        }

        post.markDeleted();
    }

    // 공통으로 사용하는 판매글 조회 메서드
    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("판매글이 존재하지 않습니다."));
    }

    // 카드 목록에서 사용할 상대 시간 문자열 생성
    private String toTimeAgo(LocalDateTime createdAt) {
        Duration duration = Duration.between(createdAt, LocalDateTime.now());
        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        if (minutes < 1) {
            return "방금 전";
        }
        if (minutes < 60) {
            return minutes + "분 전";
        }
        if (hours < 24) {
            return hours + "시간 전";
        }
        return days + "일 전";
    }

    // 판매자 엔티티를 요약 DTO로 변환
    private SellerBriefDTO toSellerBriefDTO(Member member) {
        return new SellerBriefDTO(
                member.getMemberId(),
                member.getNickname(),
                member.getProfileImageUrl(),
                member.getMannerScore()
        );
    }
}
