package com.re_form_shop_2605.service.community;


import com.re_form_shop_2605.dto.member.MemberBriefDTO;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.community.*;
import com.re_form_shop_2605.entity.Enum.CommunityPostStatus;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.entity.community.CommunityLike;
import com.re_form_shop_2605.entity.community.CommunityPost;
import com.re_form_shop_2605.entity.community.Reply;
import com.re_form_shop_2605.entity.community.ReplyLike;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.repository.community.CommunityLikeRepository;
import com.re_form_shop_2605.repository.community.CommunityPostRepository;
import com.re_form_shop_2605.repository.community.ReplyLikeRepository;
import com.re_form_shop_2605.repository.community.ReplyRepository;
import com.re_form_shop_2605.repository.member.MemberRepository;
import com.re_form_shop_2605.service.common.ServicePageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: CommunityService의 구현 클래스
 * ─────────────────────────────────────────────────────
 */
@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CommunityServiceImpl implements CommunityService {

    private final CommunityPostRepository communityPostRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final CommunityLikeRepository communityLikeRepository;
    private final ReplyLikeRepository replyLikeRepository;

    /* 게시글 목록 조회 */
    // 목록에서는 작성자를 "글쓴이" 고정 반환
    @Override
    public PageResponse<CommunityPostListItemDTO> readPosts(Sport sport, int page, int size) {

        List<CommunityPostStatus> excluded = List.of(CommunityPostStatus.HIDDEN, CommunityPostStatus.DELETED);

        List<CommunityPost> posts = (sport == null)
                ? communityPostRepository.findAllByStatusNotIn(excluded)
                : communityPostRepository.findAllBySportCategoryAndStatusNotIn(sport, excluded);

        List<CommunityPostListItemDTO> communityPostListItemDTOS = new ArrayList<>();
        for (CommunityPost post : posts) {
            communityPostListItemDTOS.add(new CommunityPostListItemDTO(
                    post.getCommId(),
                    post.getSportCategory(),
                    post.getTeamCategory(),
                    post.getCommTitle(),
                    post.getCommViewCount(),
                    post.getLikeCount(),
                    post.getCommentCount(),
                    post.getStatus(),
                    new MemberBriefDTO(null, "글쓴이", null), // 익명 처리 todo 로그인 시 아이디 테이블에 찍히게
                    post.getCreatedAt()
            ));
        }

        return ServicePageResponse.of(communityPostListItemDTOS, page, size);
    }

    /* 게시글 상세 조회 */
    // 작성자는 "글쓴이" 고정
    // isLiked: 로그인한 경우 실제 좋아요 여부, 비로그인이면 false
    @Override
    @Transactional(readOnly = true)
    public CommunityPostDetailDTO readPost(Long commId, Long viewerId) {
        CommunityPost post = getCommunityPost(commId);

        boolean isLiked = viewerId != null &&
                communityLikeRepository.existsByMember_MemberIdAndCommunityPost_CommId(viewerId, commId);

        return new CommunityPostDetailDTO(
                post.getCommId(),
                post.getSportCategory(),
                post.getTeamCategory(),
                post.getCommTitle(),
                post.getCommContent(),
                post.getCommImageUrl(),
                post.getCommViewCount(),
                post.getLikeCount(),
                post.getCommentCount(),
                isLiked,
                post.getStatus(),
                // memberId를 실제 작성자 ID로 반환 — 프론트에서 본인 글 여부 판단에 사용
                // 닉네임은 "글쓴이"로 익명 유지, profileImageUrl은 null 유지
                new MemberBriefDTO(post.getMember().getMemberId(), "글쓴이", null),
                post.getCreatedAt()
        );
    }

    /* 게시글 작성 */
    @Override
    public Long addPost(Long memberId, CommunityPostCreateRequestDTO requestDTO) {
        Member member = getMember(memberId);

        CommunityPost post = CommunityPost.builder()
                .member(member) // todo 익명 -> 로그인 안해도 예외 처리되게(?)
                .sportCategory(requestDTO.Sport())
                .teamCategory(requestDTO.teamCategory())
                .commTitle(requestDTO.commTitle())
                .commContent(requestDTO.commContent())
                .commImageUrl(requestDTO.commImageUrl())
                .commViewCount(0)
                .likeCount(0)
                .commentCount(0)
                .status(CommunityPostStatus.ACTIVE)
                .build();

        return communityPostRepository.save(post).getCommId();
    }

    /* 게시글 수정 */
    @Override
    public void modifyPost(Long commId, Long memberId, CommunityPostUpdateRequestDTO requestDTO) {
        CommunityPost post = getCommunityPost(commId);
        checkAuthor(post.getMember().getMemberId(), memberId, "게시글 수정");
        post.changePost(
                requestDTO.commTitle(),
                requestDTO.commContent(),
                requestDTO.commImageUrl()
        );
    }

    /* 게시글 삭제 */
    @Override
    public void removePost(Long commId, Long memberId) {
        CommunityPost post = getCommunityPost(commId);
        checkAuthor(post.getMember().getMemberId(), memberId, "게시글 삭제");
        post.markDeleted(); // (soft delete)
    }

    /* 게시글 좋아요 토글 */
    @Override
    public int toggleLike(Long commId, Long memberId) {
        CommunityPost post = getCommunityPost(commId);
        Member member = getMember(memberId);

        boolean alreadyLiked = communityLikeRepository.existsByMember_MemberIdAndCommunityPost_CommId(memberId, commId);

        if (alreadyLiked) {
            // 좋아요 취소
            CommunityLike like = communityLikeRepository.findByMember_MemberIdAndCommunityPost_CommId(memberId, commId)
                    .orElseThrow(() -> new IllegalArgumentException("좋아요 정보가 존재하지 않습니다.")); // todo 프론트와 어떻게 할지 상의
            communityLikeRepository.delete(like);
            post.removeLike();
        } else {
            // 좋아요 추가
            CommunityLike like = CommunityLike.builder()
                    .member(member)
                    .communityPost(post)
                    .build();
            communityLikeRepository.save(like);
            post.addLike();
        }
        return post.getLikeCount();
    }

    /* 댓글 목록 조회 */
    // 익명 처리
    // 게시글 작성자 -> 글쓴이
    // 그 외 댓글 작성자 -> 익명1, 익명2 ... (등장 순서)
    // 같은 member_id -> 같은 익명 번호 유지
    @Override
    @Transactional(readOnly = true)
    public List<ReplyResponseDTO> readReplies(Long commId, Long viewerId) {
        CommunityPost post = getCommunityPost(commId);
        Long postAuthorId = post.getMember().getMemberId();

        List<Reply> toReplies = replyRepository.findAllByCommunityPost_CommIdAndReplyIsNullOrderByCreatedAtDesc(commId);

        // 익명 번호 맵 생성
        Map<Long, Integer> annoymousMap = buildAnonymousMap(toReplies, postAuthorId);

        List<ReplyResponseDTO> result = new ArrayList<>();
        for (Reply reply : toReplies) {
            result.add(toReplyResponseDTO(reply, postAuthorId, annoymousMap,  viewerId));
        }
        return result;
    }

    /* 댓글 작성 */
    @Override
    public ReplyResponseDTO addReply(Long commId, Long memberId, ReplyCreateRequestDTO requestDTO) {
        CommunityPost post = getCommunityPost(commId);
        Member member = getMember(memberId);
        Long postAuthorId = post.getMember().getMemberId();

        Reply parent = null;
        if (requestDTO.parentId() != null) {
            parent = replyRepository.findById(requestDTO.parentId()).orElse(null); // todo 프론트와 상의 없으면 null반환?
        }

        Reply reply = Reply.builder()
                .communityPost(post)
                .member(member)
                .reply(parent)
                .replyContent(requestDTO.replyContent())
                .isDeleted(false)
                .likeCount(0)
                .build();

        Reply saved = replyRepository.save(reply);
        post.addComment();

        // 저장 후 익명 맵으로 재구성해서 반환
        List<Reply> topReplies = replyRepository
                .findAllByCommunityPost_CommIdAndReplyIsNullOrderByCreatedAtDesc(commId);
        Map<Long, Integer> anonymousMap = buildAnonymousMap(topReplies, postAuthorId);

        return toReplyResponseDTO(saved, postAuthorId, anonymousMap, memberId);
    }

    /* 댓글 삭제 */
    @Override
    public void removeReply(Long replyId, Long memberId) {
        Reply reply = getReply(replyId);
        checkAuthor(reply.getMember().getMemberId(), memberId, "댓글 삭제"); // todo 프론트와 상의 본인 댓글이 아닐 시 "권한 없음" 반환
        reply.markAsDeleted(); // 댓글 삭제 (soft delete)
        reply.getCommunityPost().removeComment(); // 커뮤니티 댓글 수 차감
    }

    /* 댓글 수정 */
    @Override
    public ReplyResponseDTO modifyReply(Long replyId, Long memberId, ReplyCreateRequestDTO requestDTO) {
        Reply reply = getReply(replyId);
        checkAuthor(reply.getMember().getMemberId(), memberId, "댓글 수정"); // 예외 던지기
        reply.changeReply(requestDTO.replyContent());

        CommunityPost post = reply.getCommunityPost();
        Long postAuthorId = post.getMember().getMemberId();
        List<Reply> topReplies = replyRepository.findAllByCommunityPost_CommIdAndReplyIsNullOrderByCreatedAtDesc(post.getCommId());
        Map<Long, Integer> anonymousMap = buildAnonymousMap(topReplies, postAuthorId);

        return toReplyResponseDTO(reply, postAuthorId, anonymousMap, memberId);
    }

    /* 댓글 좋아요 토글 */
    @Override
    public int toggleReplyLike(Long replyId, Long memberId) {
        Reply reply = getReply(replyId);
        Member member = getMember(memberId);

        boolean alreadyLiked = replyLikeRepository.existsByMember_MemberIdAndReply_ReplyId(memberId, replyId);

        if (alreadyLiked) {
            // 좋아요 취소 (delete)
            ReplyLike like = replyLikeRepository.findByMember_MemberIdAndReply_ReplyId(memberId, replyId)
                    .orElseThrow(() -> new IllegalArgumentException("좋아요 정보가 존재하지 않습니다.")); // todo 프론트와 상의 좋아요 정보가 존재하지 않을 시 null 반환?!
            replyLikeRepository.delete(like);
            reply.removeLike();
        } else {
            // 좋아요 추가 (insert)
            ReplyLike like = ReplyLike.builder()
                    .member(member)
                    .reply(reply)
                    .build();
            replyLikeRepository.save(like);
            reply.addLike();
        }

        return reply.getLikeCount();
    }

    // 최상위 댓글 + 대댓글 순서대로 순회하며
    // 처음 등장한 member_id 순서대로 1, 2, 3... 번호 부여
    // 게시글 작성자 -> "글쓴이"로 별도 처리
    private Map<Long, Integer> buildAnonymousMap(List<Reply> toReplies, Long postAuthorId) {
        Map<Long, Integer> map = new HashMap<>();
        int counter = 1;

        for (Reply reply : toReplies) {
            Long authorId = reply.getMember().getMemberId();
            if (!authorId.equals(postAuthorId) && !map.containsKey(authorId)) {
                map.put(authorId, counter++);
            }
            for (Reply child : reply.getReplies()) {
                Long childAuthorId = child.getMember().getMemberId();
                if (!childAuthorId.equals(postAuthorId) && !map.containsKey(childAuthorId)) {
                    map.put(childAuthorId, counter++);
                }
            }
        }
        return map;
    }

    // member_id -> 익명 변환("글쓴이", "익명1", "익명2"...)
    private String toAnonymousNickname(Long memberId, Long postAuthorId, Map<Long, Integer> annoymousMap) {
        if (memberId.equals(postAuthorId)) return "글쓴이";
        return "익명" + annoymousMap.get(memberId);
    }

    // Reply -> ReplyResponseDTO 변환
    private ReplyResponseDTO toReplyResponseDTO(Reply reply, Long postAuthorId, Map<Long, Integer> annoymousMap, Long viewerId) {
        // 대댓글 조회
        List<ReplyResponseDTO> children = new ArrayList<>();
        for (Reply child : reply.getReplies()) {
            children.add(toReplyResponseDTO(child, postAuthorId, annoymousMap, viewerId));
        }

        Long authorId = reply.getMember().getMemberId();
        String annoymousNickname = toAnonymousNickname(authorId, postAuthorId, annoymousMap);

        // memberId, profileImageUrl 모두 null -> 익명 처리 todo 테이블에 member_id 찍히게 해야함
        MemberBriefDTO annoymousAuthor = new MemberBriefDTO(null, annoymousNickname, null);

        // isLiked: 로그인한 경우 실제 여부 조회, 비로그인이면 false
        boolean isLiked = viewerId != null && replyLikeRepository.existsByMember_MemberIdAndReply_ReplyId(viewerId, reply.getReplyId());

        return new ReplyResponseDTO(
                reply.getReplyId(),
                annoymousAuthor,
                reply.getIsDeleted() ? "삭제된 댓글입니다." : reply.getReplyContent(),
                reply.getLikeCount(),
                isLiked,
                reply.getIsDeleted(),
                reply.getCreatedAt(),
                children
        );

    }

    // 예외 처리를 위한 메서드
    private CommunityPost getCommunityPost(Long commId) {
        return communityPostRepository.findById(commId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }

    private void checkAuthor(Long ownerId, Long requesterId, String action) {
        if (!ownerId.equals(requesterId)) {
            throw new IllegalArgumentException(action + " 권한이 없습니다.");
        }
    }

    private Reply getReply(Long replyId) {
        return replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
    }
}
