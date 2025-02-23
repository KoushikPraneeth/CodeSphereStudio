package com.codesphere.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class CollaborationSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String sessionId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    
    @ManyToMany
    private List<User> participants = new ArrayList<>();
    
    @ElementCollection
    private Set<String> activeUsers = new HashSet<>();

    public CollaborationSession() {
    }

    public CollaborationSession(Long id, String sessionId, String content, LocalDateTime createdAt, 
                              LocalDateTime lastModifiedAt, List<User> participants, Set<String> activeUsers) {
        this.id = id;
        this.sessionId = sessionId;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.participants = participants != null ? participants : new ArrayList<>();
        this.activeUsers = activeUsers != null ? activeUsers : new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants != null ? participants : new ArrayList<>();
    }

    public Set<String> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Set<String> activeUsers) {
        this.activeUsers = activeUsers != null ? activeUsers : new HashSet<>();
    }

    @Override
    public String toString() {
        return "CollaborationSession{" +
                "id=" + id +
                ", sessionId='" + sessionId + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", participants=" + participants +
                ", activeUsers=" + activeUsers +
                '}';
    }

    // Static Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String sessionId;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime lastModifiedAt;
        private List<User> participants = new ArrayList<>();
        private Set<String> activeUsers = new HashSet<>();

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder lastModifiedAt(LocalDateTime lastModifiedAt) {
            this.lastModifiedAt = lastModifiedAt;
            return this;
        }

        public Builder participants(List<User> participants) {
            this.participants = participants;
            return this;
        }

        public Builder activeUsers(Set<String> activeUsers) {
            this.activeUsers = activeUsers;
            return this;
        }

        public CollaborationSession build() {
            return new CollaborationSession(id, sessionId, content, createdAt, lastModifiedAt, participants, activeUsers);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollaborationSession that = (CollaborationSession) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return sessionId != null ? sessionId.equals(that.sessionId) : that.sessionId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        return result;
    }
}
