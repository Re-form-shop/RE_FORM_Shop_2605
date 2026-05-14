package com.re_form_shop_2605.controller.chat;

import com.re_form_shop_2605.dto.chat.ChatMessageDTO;
import com.re_form_shop_2605.dto.chat.ChatSendMessageDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-12
 * 설명: STOMP 프로토콜을 사용한 웹소켓 메시지 처리를 담당하는 컨트롤러
 * ─────────────────────────────────────────────────────
 */
@Log4j2
@Controller
@RequiredArgsConstructor
public class StompChatController {
    /* WebSocket은 그냥 "연결 통로" 만 제공하고, STOMP는 그 위에서 "어떤 형식으로 메시지를 주고받을지" 를 정의한 프로토콜입니다. */
    private final ChatService chatService;
    // 서버 → 클라이언트로 메시지를 능동적으로 보낼 때 사용하는 클래스, 서버가 먼저, 언제든지 특정 경로를 구독 중인 클라이언트에게 메시지를 푸시할 수 있음.
    private final SimpMessagingTemplate simpMessagingTemplate;

    // 클라이언트가 /pub/chat/message로 보내면 여기서 처리
    @MessageMapping("/chat/message")
    public void handleMessage(ChatSendMessageDTO chatSendMessageDTO, Principal principal) {
        MemberSecurityDTO member = resolveMember(principal);
        log.info("Received message: chatId={}, sender={}", chatSendMessageDTO.chatId(), member.getMemberId());

        // 1. DB 저장
        ChatMessageDTO saved = chatService.saveMessage(chatSendMessageDTO, member.getMemberId());

        // 2. 해당 채팅방 구독자에게 전송
        // todo React 클라이언트는 /sub/chat/{chatId} 를 구독하고 있어야 함
        simpMessagingTemplate.convertAndSend("/sub/chat/" + chatSendMessageDTO.chatId(), saved);
    }

    // 채팅방 입장 시 읽음 처리
    @MessageMapping("/chat/read")
    public void handleRead(@Payload Long chatId, Principal principal) {
        MemberSecurityDTO member = resolveMember(principal);
        chatService.markAsRead(chatId, member.getMemberId());
    }

    private MemberSecurityDTO resolveMember(Principal principal) {
        if (principal instanceof org.springframework.security.core.Authentication authentication
                && authentication.getPrincipal() instanceof MemberSecurityDTO member) {
            return member;
        }
        throw new IllegalArgumentException("STOMP 인증 정보가 없습니다.");
    }
}
