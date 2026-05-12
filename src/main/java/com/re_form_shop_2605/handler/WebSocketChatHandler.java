package com.re_form_shop_2605.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-08
 * 설명: 웹소켓 채팅 연결의 생명주기(연결/메시지/종료)를 처리하는 핸들러
 * ─────────────────────────────────────────────────────
 */
@Log4j2
@Component
public class WebSocketChatHandler extends TextWebSocketHandler {

    // 현재 서버에 접속한 클라이언트들을 저장
    final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        /* 클라이언트와 웹소켓 연결이 성립된 직후 호출 */
        log.info("{} connected", session.getId());

        // 연결이 되면 접속자 명단에 추가
        this.webSocketSessionMap.put(session.getId(), session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        /* 클라이언트가 보낸 메시지를 수신했을 때 호출 */
        log.info("{} sent {}", session.getId(), message.getPayload());

        this.webSocketSessionMap.values().forEach(webSocketSession -> {
            try {
                webSocketSession.sendMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        /* 웹소켓 연결이 종료된 뒤 호출 */
        log.info("{} disconnected", session.getId());

        // 연결을 종료하면 접속자 명단에서 제거
        this.webSocketSessionMap.remove(session.getId());
    }
}
