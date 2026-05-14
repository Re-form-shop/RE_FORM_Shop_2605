package com.re_form_shop_2605.mapper.trade;

import com.re_form_shop_2605.domain.trade.MannerReviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 매너 리뷰 관련 매퍼 인터페이스
 * ─────────────────────────────────────────────────────
 */
@Mapper
public interface MannerReviewMapper {
    // 매너 리뷰 등록
    int insertMannerReview(MannerReviewVO mannerReview);

    // 리뷰 번호로 단건 조회
    MannerReviewVO findMannerReviewById(@Param("mannerId") Long mannerId);

    // 거래 번호와 작성자 번호 기준 단건 조회
    MannerReviewVO findMannerReviewByTradeIdAndBuyerId(@Param("tradeId") Long tradeId,
                                                       @Param("buyerId") Long buyerId);

    // 판매자 회원 번호 기준 리뷰 목록 조회
    List<MannerReviewVO> findMannerReviewsBySellerId(@Param("sellerId") Long sellerId);
}
