package com.codesphere.config;

import com.codesphere.service.CollaborationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final CollaborationService collaborationService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = headerAccessor.getUser().getName();
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        
        if (username != null && sessionId != null) {
            collaborationService.leaveSession(sessionId, username);
        }
    }
}