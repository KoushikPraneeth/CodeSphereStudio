package com.codesphere.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
public class CollaborationSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String sessionId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    
    @Builder.Default
    @ManyToMany
    private List<User> participants = new ArrayList<>();
    
    @Builder.Default
    @ElementCollection
    private Set<String> activeUsers = new HashSet<>();
}
