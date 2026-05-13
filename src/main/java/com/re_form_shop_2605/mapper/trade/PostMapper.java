package com.re_form_shop_2605.mapper.trade;

import com.re_form_shop_2605.domain.trade.PostVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * 작성자: 민기
 * 작성일: 2026-05-08
 * 설명: 게시글 관련 매퍼 인터페이스
 */
@Mapper
public interface PostMapper {
    // 게시글 등록
    int insertPost(PostVO post);

    // 게시글 번호로 단건 조회
    PostVO findPostById(@Param("postId") Long postId);

    // 전체 게시글 목록 조회
    List<PostVO> findAllPosts();

    // 게시글 전체 정보 수정
    int updatePost(PostVO post);

    // 게시글 상태만 수정
    int updatePostStatus(@Param("postId") Long postId, @Param("status") String status);
}
