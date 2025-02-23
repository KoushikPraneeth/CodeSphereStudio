package com.codesphere.repository;

import com.codesphere.model.CollaborationSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollaborationSessionRepository extends JpaRepository<CollaborationSession, Long> {
    Optional<CollaborationSession> findBySessionId(String sessionId);
}