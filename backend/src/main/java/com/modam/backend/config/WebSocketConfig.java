package com.modam.backend.config;

import com.modam.backend.security.JwtHandshakeInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //인터셉터 등록: 0613
    private final JwtHandshakeInterceptor jwtHandshakeInterceptor;

    // 환경 변수 주입
    @Value("${ai.server.url}")
    private String aiServerUrl;
    @Value("${websocket.allowed.origin}")
    private String allowedOrigin;

    public WebSocketConfig(JwtHandshakeInterceptor jwtHandshakeInterceptor) {
        this.jwtHandshakeInterceptor = jwtHandshakeInterceptor;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  // 구독 경로
        config.setApplicationDestinationPrefixes("/app");  // 메시지 전송 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")
                .setAllowedOriginPatterns(allowedOrigin)  // React 서버 포트 허용
                .addInterceptors(jwtHandshakeInterceptor)// 인터셉터 등록
                .withSockJS();  // SockJS 활성화: 임시 주석처리 0613

    }
}
