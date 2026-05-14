package com.re_form_shop_2605.mapper.trade;

import com.re_form_shop_2605.domain.trade.TradeVO;
import com.re_form_shop_2605.entity.Enum.TradeStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-08
 * 설명: 거래 관련 매퍼 인터페이스
 * ─────────────────────────────────────────────────────
 */
@Mapper
public interface TradeMapper {
    // 거래 등록
    int insertTrade(TradeVO trade);

    // 거래 번호로 단건 조회
    TradeVO findTradeById(@Param("tradeId") Long tradeId);

    // 게시글 번호로 거래 조회
    TradeVO findTradeByPostId(@Param("postId") Long postId);

    // 구매자 회원 번호로 거래 목록 조회
    List<TradeVO> findTradesByBuyerId(@Param("buyerId") Long buyerId);

    // 판매자 회원 번호로 거래 목록 조회
    List<TradeVO> findTradesBySellerId(@Param("sellerId") Long sellerId);

    // 거래 전체 정보 수정
    int updateTrade(TradeVO trade);

    // 거래 상태만 수정
    int updateTradeStatus(@Param("tradeId") Long tradeId, @Param("status") TradeStatus status);
}
