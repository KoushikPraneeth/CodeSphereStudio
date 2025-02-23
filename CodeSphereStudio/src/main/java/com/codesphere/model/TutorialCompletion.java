package com.codesphere.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TutorialCompletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private User user;
    
    @ManyToOne
    private Tutorial tutorial;
    
    private Integer stepIndex;
    private LocalDateTime completedAt;

    public TutorialCompletion() {
    }

    public TutorialCompletion(Long id, User user, Tutorial tutorial, Integer stepIndex, LocalDateTime completedAt) {
        this.id = id;
        this.user = user;
        this.tutorial = tutorial;
        this.stepIndex = stepIndex;
        this.completedAt = completedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    public Integer getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(Integer stepIndex) {
        this.stepIndex = stepIndex;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @Override
    public String toString() {
        return "TutorialCompletion{" +
                "id=" + id +
                ", user=" + user +
                ", tutorial=" + tutorial +
                ", stepIndex=" + stepIndex +
                ", completedAt=" + completedAt +
                '}';
    }

    // Static Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private User user;
        private Tutorial tutorial;
        private Integer stepIndex;
        private LocalDateTime completedAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder tutorial(Tutorial tutorial) {
            this.tutorial = tutorial;
            return this;
        }

        public Builder stepIndex(Integer stepIndex) {
            this.stepIndex = stepIndex;
            return this;
        }

        public Builder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public TutorialCompletion build() {
            return new TutorialCompletion(id, user, tutorial, stepIndex, completedAt);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TutorialCompletion that = (TutorialCompletion) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
