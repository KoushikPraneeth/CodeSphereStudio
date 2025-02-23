package com.codesphere.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TutorialStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private String initialCode;
    private String expectedOutput;
    private String hint;
    private String language;
    
    @ElementCollection
    private List<String> validationTests = new ArrayList<>();

    public TutorialStep() {
    }

    public TutorialStep(Long id, String title, String description, String initialCode, 
                       String expectedOutput, String hint, String language, List<String> validationTests) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.initialCode = initialCode;
        this.expectedOutput = expectedOutput;
        this.hint = hint;
        this.language = language;
        this.validationTests = validationTests != null ? validationTests : new ArrayList<>();
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

    public String getInitialCode() {
        return initialCode;
    }

    public void setInitialCode(String initialCode) {
        this.initialCode = initialCode;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getValidationTests() {
        return validationTests;
    }

    public void setValidationTests(List<String> validationTests) {
        this.validationTests = validationTests != null ? validationTests : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "TutorialStep{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", initialCode='" + initialCode + '\'' +
                ", expectedOutput='" + expectedOutput + '\'' +
                ", hint='" + hint + '\'' +
                ", language='" + language + '\'' +
                ", validationTests=" + validationTests +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TutorialStep that = (TutorialStep) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
