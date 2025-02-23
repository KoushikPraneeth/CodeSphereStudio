package com.codesphere.model;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorsMessage {
    private List<String> collaborators;

    public CollaboratorsMessage() {
        this.collaborators = new ArrayList<>();
    }

    public CollaboratorsMessage(List<String> collaborators) {
        this.collaborators = collaborators != null ? collaborators : new ArrayList<>();
    }

    public List<String> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<String> collaborators) {
        this.collaborators = collaborators != null ? collaborators : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "CollaboratorsMessage{" +
                "collaborators=" + collaborators +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollaboratorsMessage that = (CollaboratorsMessage) o;

        return collaborators != null ? collaborators.equals(that.collaborators) : that.collaborators == null;
    }

    @Override
    public int hashCode() {
        return collaborators != null ? collaborators.hashCode() : 0;
    }
}
