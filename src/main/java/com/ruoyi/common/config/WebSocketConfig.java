package com.ruoyi.common.config;
 
import javax.annotation.Resource;

import com.ruoyi.web.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.ruoyi.web.websocket.DefaultInterceptor;

@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
 
    
    @Autowired
    private WebSocketHandler webSocket;
    
    @Autowired
    private DefaultInterceptor defaultInterceptor;
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocket, "/ws/message")
                .addInterceptors(defaultInterceptor)
                .setAllowedOrigins("*"); // 解决跨域问题 [4]
    }
}