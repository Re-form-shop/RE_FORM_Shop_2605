package com.re_form_shop_2605.repository.chat;

import com.re_form_shop_2605.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-10
 * 설명: 채팅방 Repository
 * ─────────────────────────────────────────────────────
 */
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 같은 판매글, 같은 구매자 채팅방 중복 확인 -> DB에서 복합유니크로 보장 되지만 코드에서 확인용으로 사용
    Optional<ChatRoom> findByPost_PostIdAndBuyer_MemberId(Long postId, Long buyerId);
    Optional<ChatRoom> findFirstByTrade_TradeId(Long tradeId);

    // 내 채팅방 목록 조회
    // 구매자로 참여한 방, 판매자(post 작성자)로 참여한 방 모두 조회
    List<ChatRoom> findByBuyer_MemberIdOrderByCreatedAtDesc(Long buyerId);
    List<ChatRoom> findByPost_PostIdOrderByCreatedAtDesc(Long postId);
    /* 판매자(memberId)로 참여한 채팅방 목록 — post.sellerId.memberId 기준 */
    List<ChatRoom> findByPost_SellerId_MemberIdOrderByCreatedAtDesc(Long sellerId);
}
