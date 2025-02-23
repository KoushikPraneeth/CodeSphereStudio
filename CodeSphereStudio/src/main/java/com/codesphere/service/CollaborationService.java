package com.codesphere.service;

import com.codesphere.model.CollaborationMessage;
import com.codesphere.model.CollaborationSession;
import com.codesphere.model.User;
import com.codesphere.repository.CollaborationSessionRepository;
import com.codesphere.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collections;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CollaborationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final CollaborationSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final Map<String, Set<String>> activeCollaborators = new ConcurrentHashMap<>();

    @Transactional
    public CollaborationSession createSession(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        CollaborationSession session = new CollaborationSession();
        session.setSessionId(UUID.randomUUID().toString());
        session.setCreatedAt(LocalDateTime.now());
        session.setLastModifiedAt(LocalDateTime.now());
        session.getParticipants().add(user);
        session.getActiveUsers().add(username);

        return sessionRepository.save(session);
    }

    @Transactional
    public void joinSession(String sessionId, String username) {
        CollaborationSession session = sessionRepository.findBySessionId(sessionId)
            .orElseThrow(() -> new RuntimeException("Session not found"));

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        session.getParticipants().add(user);
        session.getActiveUsers().add(username);
        sessionRepository.save(session);

        // Notify other participants
        messagingTemplate.convertAndSend(
            "/topic/session/" + sessionId + "/users",
            session.getActiveUsers()
        );
    }

    @Transactional
    public void leaveSession(String sessionId, String username) {
        CollaborationSession session = sessionRepository.findBySessionId(sessionId)
            .orElseThrow(() -> new RuntimeException("Session not found"));

        session.getActiveUsers().remove(username);
        sessionRepository.save(session);

        messagingTemplate.convertAndSend(
            "/topic/session/" + sessionId + "/users",
            session.getActiveUsers()
        );
    }

    @Transactional
    public void handleMessage(CollaborationMessage message) {
        CollaborationSession session = sessionRepository.findBySessionId(message.getSessionId())
            .orElseThrow(() -> new RuntimeException("Session not found"));

        switch (message.getType()) {
            case "CODE_CHANGE":
                session.setContent(message.getContent());
                session.setLastModifiedAt(LocalDateTime.now());
                sessionRepository.save(session);
                break;
            case "CURSOR_MOVE":
                messagingTemplate.convertAndSend(
                    "/topic/session/" + message.getSessionId() + "/cursors",
                    Map.of("username", message.getUsername(), "position", message.getPosition())
                );
                return;
            case "CHAT":
                messagingTemplate.convertAndSend(
                    "/topic/session/" + message.getSessionId() + "/chat",
                    Map.of("username", message.getUsername(), "message", message.getContent())
                );
                return;
        }

        // Broadcast code changes to all participants
        messagingTemplate.convertAndSend(
            "/topic/session/" + message.getSessionId(),
            message
        );
    }

    public Optional<CollaborationSession> getSession(String sessionId) {
        return sessionRepository.findBySessionId(sessionId);
    }

    public void handleUserJoin(String sessionId, String username) {
        activeCollaborators.computeIfAbsent(sessionId, k -> ConcurrentHashMap.newKeySet())
            .add(username);
        
        broadcastCollaborators(sessionId);
    }

    public void handleUserLeave(String sessionId, String username) {
        activeCollaborators.computeIfPresent(sessionId, (k, v) -> {
            v.remove(username);
            return v.isEmpty() ? null : v;
        });
        
        broadcastCollaborators(sessionId);
    }

    public void broadcastCodeChange(String sessionId, String username, String code) {
        CodeChangeMessage message = new CodeChangeMessage(username, code);
        messagingTemplate.convertAndSend("/topic/session/" + sessionId + "/code", message);
    }

    private void broadcastCollaborators(String sessionId) {
        Set<String> collaborators = activeCollaborators.getOrDefault(sessionId, Collections.emptySet());
        messagingTemplate.convertAndSend(
            "/topic/session/" + sessionId + "/collaborators",
            new CollaboratorsMessage(new ArrayList<>(collaborators))
        );
    }
}
