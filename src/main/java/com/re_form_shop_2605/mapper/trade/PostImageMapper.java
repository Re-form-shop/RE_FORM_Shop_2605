package com.re_form_shop_2605.mapper.trade;

import com.re_form_shop_2605.domain.trade.PostImageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 게시글 이미지 관련 매퍼 인터페이스
 * ─────────────────────────────────────────────────────
 */
@Mapper
public interface PostImageMapper {
    // 게시글 이미지 등록
    int insertPostImage(PostImageVO postImage);

    // 게시글 번호로 이미지 목록 조회
    List<PostImageVO> findPostImagesByPostId(@Param("postId") Long postId);

    // 게시글 번호 기준 전체 이미지 삭제
    int deletePostImagesByPostId(@Param("postId") Long postId);
}
