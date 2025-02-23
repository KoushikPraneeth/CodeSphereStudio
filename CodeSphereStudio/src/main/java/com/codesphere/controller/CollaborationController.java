package com.codesphere.controller;

import com.codesphere.model.CollaborationMessage;
import com.codesphere.model.CollaborationSession;
import com.codesphere.service.CollaborationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CollaborationController {
    private final CollaborationService collaborationService;

    @PostMapping("/api/collaboration/sessions")
    public ResponseEntity<CollaborationSession> createSession(Authentication authentication) {
        CollaborationSession session = collaborationService.createSession(authentication.getName());
        return ResponseEntity.ok(session);
    }

    @MessageMapping("/session.join")
    public void joinSession(@Payload String sessionId, SimpMessageHeaderAccessor headerAccessor) {
        String username = headerAccessor.getUser().getName();
        collaborationService.joinSession(sessionId, username);
    }

    @MessageMapping("/session.leave")
    public void leaveSession(@Payload String sessionId, SimpMessageHeaderAccessor headerAccessor) {
        String username = headerAccessor.getUser().getName();
        collaborationService.leaveSession(sessionId, username);
    }

    @MessageMapping("/collaborate")
    public void handleCollaboration(@Payload CollaborationMessage message, 
                                  SimpMessageHeaderAccessor headerAccessor) {
        String username = headerAccessor.getUser().getName();
        message.setUsername(username);
        collaborationService.handleMessage(message);
    }

    @GetMapping("/api/collaboration/sessions/{sessionId}")
    public ResponseEntity<CollaborationSession> getSession(@PathVariable String sessionId) {
        return collaborationService.getSession(sessionId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
