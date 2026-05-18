package com.re_form_shop_2605.controller.chat;

import com.re_form_shop_2605.dto.chat.ChatMessageDTO;
import com.re_form_shop_2605.dto.chat.ChatReadRequestDTO;
import com.re_form_shop_2605.dto.chat.ChatSendMessageDTO;
import com.re_form_shop_2605.dto.login.MemberSecurityDTO;
import com.re_form_shop_2605.dto.AI.RiskAnalysisResultDTO;
import com.re_form_shop_2605.entity.Enum.TargetType;
import com.re_form_shop_2605.service.chat.ChatService;
import com.re_form_shop_2605.service.AI.ModerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

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
    private final ModerationService moderationService;

    // 클라이언트가 /pub/chat/message로 보내면 여기서 처리
    @MessageMapping("/chat/message")
    public void handleMessage(ChatSendMessageDTO chatSendMessageDTO, Principal principal) {
        MemberSecurityDTO member = resolveMember(principal);
        log.info("Received message: chatId={}, sender={}", chatSendMessageDTO.chatId(), member.getMemberId());

        // 1. 메세지 선저장 (safe 기본값)
        // Moderation 검사에 messageId가 필요하므로 먼저 저장.
        ChatMessageDTO saved = chatService.saveMessage(chatSendMessageDTO, null);

        // 2. MessageType(TEXT / IMAGE / SYSTEM) 중 TEXT 타입만 Moderation 검사
        if ("TEXT".equalsIgnoreCase(chatSendMessageDTO.type())) {
            // CHAT 타입으로 검사 — messageId를 targetId로 전달
            RiskAnalysisResultDTO moderation = moderationService.checkAndSave(
                    chatSendMessageDTO.content(),
                    TargetType.CHAT,
                    saved.messageId()
            );
            log.info("[Moderation] chatId={}, riskLevel={}", chatSendMessageDTO.chatId(), moderation.riskLevel());

            // 3. ChatMessageDTO moderation 필드 교체
            // ChatMessage 엔티티 수정 없음, DTO의 moderation만 실제 검사 결과로 교체해서 전송
            saved = new ChatMessageDTO(
                    saved.messageId(),
                    saved.senderId(),
                    saved.content(),
                    saved.type(),
                    saved.createdAt(),
                    saved.isRead(),
                    moderation  // safe() -> 실제 검사 결과로 교체
            );
        }

        // 4. 해당 채팅방 구독자에게 전송
        // todo React 클라이언트는 /sub/chat/{chatId} 를 구독하고 있어야 함
        simpMessagingTemplate.convertAndSend("/sub/chat/" + chatSendMessageDTO.chatId(), saved);

        // 5. 상대방 개인 구독 경로로 알림 푸시 (실시간 배지 갱신용)
        // chatService.saveMessage 내에서 이미 DB 저장은 완료됨
        // 여기서는 STOMP 실시간 전송만 담당
        //
        // 클라이언트 구독 경로: /sub/user/{receiverId}/notification
        // convertAndSendToUser(username, destination, payload) 형태를 쓰면
        // -> 실제 전송 경로는 /sub/user/{username}/notification 이 됨
        //
        // STOMP 인증 시 Principal의 getName()이 memberId(String)이므로
        // receiver의 memberId를 String으로 변환해서 사용
        Long receiverId = chatService.resolveReceiverId(
                chatSendMessageDTO.chatId(),
                member.getMemberId()
        );

        // 알림 : 클라이언트가 배지 표시에 필요한 최소 정보
        Map<String, Object> notificationPayload = Map.of(
                "type", "CHAT",                         // 알림 종류
                "chatId", chatSendMessageDTO.chatId(),  // 채팅방 ID
                "senderNickname", member.getNickname(), // 발신자 닉네임
                "content", chatSendMessageDTO.content() // 메시지 미리보기
        );

        // 수신자 알림 채널로 push — GNB 배지 실시간 갱신용
        // 클라이언트는 /sub/notification/{memberId} 구독 중
        simpMessagingTemplate.convertAndSend(
                "/sub/notification/" + receiverId,
                (Object) notificationPayload  // (Object) 캐스트: convertAndSend(String, Object) 오버로드 명확히 지정
        );
    }

    // 채팅방 입장 시 읽음 처리
    @MessageMapping("/chat/read")
    public void handleRead(@Payload ChatReadRequestDTO chatReadRequestDTO) {
        chatService.markAsRead(chatReadRequestDTO.chatId(), chatReadRequestDTO.myId());
    }

    private MemberSecurityDTO resolveMember(Principal principal) {
        if (principal instanceof org.springframework.security.core.Authentication authentication
                && authentication.getPrincipal() instanceof MemberSecurityDTO member) {
            return member;
        }
        throw new IllegalArgumentException("STOMP 인증 정보가 없습니다.");
    }
}
