package com.re_form_shop_2605.domain.chat;

import com.re_form_shop_2605.domain.etc.RiskAnalysisResultVO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-08
 * 설명: 채팅 메세지 VO
 * ─────────────────────────────────────────────────────
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageVO {
    private Long messageId; // 메시지 ID
    private Long chatId; // 채팅방 ID
    private Long senderId; // 발신자 member_id
    private String content; // 메시지 내용 (이미지 전송 시 NULL 가능)
    private String type; // 메시지 타입 (TEXT / IMAGE / SYSTEM)
    private Boolean isRead; // 읽음 여부
    LocalDateTime createdAt; // 메시지 전송 시각
    private RiskAnalysisResultVO riskAnalysisResultVO;
}
