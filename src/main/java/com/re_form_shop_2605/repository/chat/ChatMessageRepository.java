package com.re_form_shop_2605.repository.chat;

import com.re_form_shop_2605.entity.chat.ChatMessage;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-10
 * 설명: 채팅 메세지 Repository
 * ─────────────────────────────────────────────────────
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 채팅방 메시지 이력 페이징 조회
    Page<ChatMessage> findByChatRoom_ChatIdOrderByCreatedAtDesc(Long chatId, Pageable pageable);

    // 내 아이디가 아니면서 / 읽지 않은 메시지 수 (채팅방 목록 배지용)
    long countByChatRoom_ChatIdAndIsReadFalseAndMember_MemberIdNot(Long chatId, Long myId);

    // 채팅방 입장 시 미읽음 메세지 읽음 처리
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE ChatMessage m set m.isRead = true " +
            "WHERE m.chatRoom.chatId = :chatId " +
            "AND m.member.memberId != :myId " +
            "AND m.isRead = false")
    void markAllAsRead(@Param("chatId") Long chatId, @Param("myId") Long myId);
}