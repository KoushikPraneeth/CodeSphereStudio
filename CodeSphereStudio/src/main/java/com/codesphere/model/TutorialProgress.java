package com.codesphere.model;

import jakarta.persistence.*;

@Entity
public class TutorialProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Tutorial tutorial;

    private int currentStep;
    private boolean completed;

    public TutorialProgress() {
    }

    public TutorialProgress(Long id, User user, Tutorial tutorial, int currentStep, boolean completed) {
        this.id = id;
        this.user = user;
        this.tutorial = tutorial;
        this.currentStep = currentStep;
        this.completed = completed;
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

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "TutorialProgress{" +
                "id=" + id +
                ", user=" + user +
                ", tutorial=" + tutorial +
                ", currentStep=" + currentStep +
                ", completed=" + completed +
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
        private int currentStep;
        private boolean completed;

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

        public Builder currentStep(int currentStep) {
            this.currentStep = currentStep;
            return this;
        }

        public Builder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public TutorialProgress build() {
            return new TutorialProgress(id, user, tutorial, currentStep, completed);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TutorialProgress that = (TutorialProgress) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
