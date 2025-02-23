package com.codesphere.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tutorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private String difficulty;
    private String category;
    private Integer estimatedMinutes;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tutorial_id")
    private List<TutorialStep> steps = new ArrayList<>();
    
    @OneToMany(mappedBy = "tutorial", cascade = CascadeType.ALL)
    private List<TutorialCompletion> completions = new ArrayList<>();
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Tutorial() {
    }

    public Tutorial(Long id, String title, String description, String difficulty, String category,
                   Integer estimatedMinutes, List<TutorialStep> steps, List<TutorialCompletion> completions,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.category = category;
        this.estimatedMinutes = estimatedMinutes;
        this.steps = steps != null ? steps : new ArrayList<>();
        this.completions = completions != null ? completions : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(Integer estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }

    public List<TutorialStep> getSteps() {
        return steps;
    }

    public void setSteps(List<TutorialStep> steps) {
        this.steps = steps != null ? steps : new ArrayList<>();
    }

    public List<TutorialCompletion> getCompletions() {
        return completions;
    }

    public void setCompletions(List<TutorialCompletion> completions) {
        this.completions = completions != null ? completions : new ArrayList<>();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Tutorial{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", category='" + category + '\'' +
                ", estimatedMinutes=" + estimatedMinutes +
                ", steps=" + steps +
                ", completions=" + completions +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tutorial tutorial = (Tutorial) o;

        return id != null ? id.equals(tutorial.id) : tutorial.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
