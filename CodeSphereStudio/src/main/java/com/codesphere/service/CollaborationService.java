package com.codesphere.service;

import com.codesphere.model.*;
import com.codesphere.repository.CollaborationSessionRepository;
import com.codesphere.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CollaborationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final CollaborationSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final Map<String, Set<String>> activeCollaborators = new ConcurrentHashMap<>();

    public CollaborationService(SimpMessagingTemplate messagingTemplate,
                                  CollaborationSessionRepository sessionRepository,
                                  UserRepository userRepository) {
        this.messagingTemplate = messagingTemplate;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CollaborationSession createSession(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        CollaborationSession session = CollaborationSession.builder()
            .sessionId(UUID.randomUUID().toString())
            .createdAt(LocalDateTime.now())
            .lastModifiedAt(LocalDateTime.now())
            .participants(new ArrayList<>())
            .activeUsers(new HashSet<>())
            .build();
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

        messagingTemplate.convertAndSend(
            "/topic/session/" + message.getSessionId(),
            message
        );
    }

    public Optional<CollaborationSession> getSession(String sessionId) {
        return sessionRepository.findBySessionId(sessionId);
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
