package com.re_form_shop_2605.entity.chat;

import com.re_form_shop_2605.entity.BaseEntity;
import com.re_form_shop_2605.entity.member.Member;
import com.re_form_shop_2605.entity.trade.Post;
import com.re_form_shop_2605.entity.trade.Trade;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-07
 * 설명: 채팅방의 Entity
 * ─────────────────────────────────────────────────────
 */
@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "chat_room",
        uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "buyer_id"}))
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id", updatable = false)
    private Trade trade; // 거래 ID (거래 요청 후 연결 시 채워짐)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="buyer_id", nullable = false)
    private Member buyer; // 구매자 member_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // 게시글 ID (판매자는 post.getSellerId()로 접근)

    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public void connectTrade(Trade trade) {
        this.trade = trade;
    }
}
