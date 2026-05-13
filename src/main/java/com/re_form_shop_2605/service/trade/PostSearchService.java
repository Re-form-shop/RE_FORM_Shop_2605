/**
 * 작성자: 손민정
 * 작성일: 2026-05-13
 * 설명: 의미 기반 유사 상품 검색 - 검색 서비스 구현
 */
package com.re_form_shop_2605.service.trade;

import com.re_form_shop_2605.domain.trade.PostCardVO;
import com.re_form_shop_2605.dto.common.PageResponse;
import com.re_form_shop_2605.dto.trade.PostCardDTO;
import com.re_form_shop_2605.dto.trade.SellerBriefDTO;
import com.re_form_shop_2605.entity.Enum.DeliveryType;
import com.re_form_shop_2605.entity.Enum.Grade;
import com.re_form_shop_2605.entity.Enum.Sport;
import com.re_form_shop_2605.mapper.trade.PostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostSearchService {
    private final VectorStore vectorStore;
    private final PostMapper postMapper;

    /* 검색어 적용 결과 목록 반환 */
    public PageResponse<PostCardDTO> search(String query, Sport sport, Grade condition,
                                    DeliveryType tradeType, Integer minPrice, Integer maxPrice,
                                    String sort, int page, int size, Long memberId) {
        Set<Long> postIdSet = new LinkedHashSet<>();

        // 1) AI 통한 유사 의미 결과 검색
        if (query != null && !query.isBlank()) {
            List<Document> similaritySearch = vectorStore.similaritySearch(
                    SearchRequest.builder()
                            .query(query)
                            .topK(100)
                            .similarityThreshold(0.7)
                            .build());
            similaritySearch.forEach(result -> {
                Object postId = result.getMetadata().get("postId");
                if (postId != null) {
                    Long id = Long.parseLong(postId.toString());
                    log.info("AI 검색 결과 postId: {}, score: {}", id, result.getScore());
                    postIdSet.add(id);
                }
            });
        }

        // 2) 키워드 검색
        List<PostCardVO> keywordResults = postMapper.findPostsByCondition(
                query, sport, condition, tradeType, minPrice, maxPrice, sort, 0, Integer.MAX_VALUE, memberId
        );
        keywordResults.forEach(post -> { postIdSet.add(post.getPostId()); });



        // 3) postIds가 비어있을 경우, 빈 결과 반환
        if (postIdSet.isEmpty()) {
            return new PageResponse<>(List.of(), 0, 0, size, page, true, true);
        }

        // 4) postIdSet 리스트 변환 후 페이징 처리
        List<Long> postIds = new ArrayList<>(postIdSet);
        int totalElements = postMapper.countPostsByIds(postIds, sport, condition, tradeType, minPrice, maxPrice);
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int offset = page * size;
        List<Long> pageIds = postIds.stream()
                .skip(offset)
                .limit(size)
                .toList();

        // 5) 상세 정보 조회
        List<PostCardVO> posts = postMapper.findPostsByIds(pageIds, memberId, sport, condition, tradeType, minPrice, maxPrice);

        // 6) DTO 변환
        List<PostCardDTO> content = posts.stream().map(post -> {
            SellerBriefDTO seller = new SellerBriefDTO(
                    post.getSellerMemberId(),
                    post.getSellerNickname(),
                    post.getSellerProfileImageUrl(),
                    post.getSellerMannerScore()
            );
            return new PostCardDTO(
                    post.getPostId(), post.getTitle(), post.getTeam(), post.getSport(),
                    post.getPrice(), post.getCondition(), post.getSize(), post.getTradeType(),
                    post.getStatus(), post.getViewCount(), post.getLikeCount(), post.isLiked(),
                    post.getThumbnailUrl(), null, post.getCreatedAt(), seller
            );
        }).toList();

        // 7) PageResponse 반환
        boolean first = page == 0;
        boolean last = page >= totalPages - 1;

        return new PageResponse<>(content, totalElements, totalPages, size, page, first, last);
    }
}