package com.re_form_shop_2605.entity.chat;

import com.re_form_shop_2605.entity.BaseEntity;
import com.re_form_shop_2605.entity.Enum.MessageType;
import com.re_form_shop_2605.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-11
 * 설명: 채팅 메세지의 Entity
 * ─────────────────────────────────────────────────────
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_message")
public class ChatMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatRoom chatRoom; // 어느 채팅방의 메시지인지

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member member; // 보낸 사람

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // 메시지 내용

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MessageType type; // TEXT / IMAGE / SYSTEM

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false; // 읽음 여부
}
