package com.codesphere.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ProjectFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String content;
    private String language;
    private String owner;
    
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    
    @Version
    private Long version;
}