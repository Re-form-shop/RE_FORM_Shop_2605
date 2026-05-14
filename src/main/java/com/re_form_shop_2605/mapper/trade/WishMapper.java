package com.re_form_shop_2605.mapper.trade;

import com.re_form_shop_2605.domain.trade.WishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 찜 정보 관련 매퍼 인터페이스
 * ─────────────────────────────────────────────────────
 */
@Mapper
public interface WishMapper {
    // 회원 번호와 게시글 번호 기준 찜 단건 조회
    WishVO findWishByMemberIdAndPostId(@Param("memberId") Long memberId, @Param("postId") Long postId);
}
