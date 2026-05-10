package com.re_form_shop_2605.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    /* STOMP 프로토콜을 사용한 웹소켓 메시지 브로커 설정 클래스 */

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /* 클라이언트가 웹소켓 연결을 시도할 때 사용할 엔드포인트를 등록 */
        // /stomp/chat: 클라이언트가 접속할 웹소켓 URL
        registry.addEndpoint("/stomp/chat")
                .setAllowedOriginPatterns("*"); //
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /* 메시지 브로커를 구성하여 클라이언트가 메시지를 발행하거나 구독할 때 사용할 경로를 설정 */
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub");
    }
}
