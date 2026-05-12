package com.re_form_shop_2605.config;

import com.re_form_shop_2605.handler.WebSocketChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * ─────────────────────────────────────────────────────
 * 작성자: 진혜림
 * 작성일: 2026-05-08
 * 설명: 스프링 웹소켓 활성화 및 핸들러 매핑을 담당하는 설정 클래스
 * ─────────────────────────────────────────────────────
 */
@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketChatHandler webSocketChatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        /* 웹소켓 핸들러를 엔드포인트에 등록 */
        // /ws/chat: 클라이언트가 접속할 웹소켓 URL
        registry.addHandler(webSocketChatHandler, "/ws/chat");
    }
}
