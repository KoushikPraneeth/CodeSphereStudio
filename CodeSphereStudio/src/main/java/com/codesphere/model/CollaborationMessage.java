package com.codesphere.model;

public class CollaborationMessage {
    private String sessionId;
    private String username;
    private String type;
    private String content;
    private Position position;

    public CollaborationMessage() {
    }

    public CollaborationMessage(String sessionId, String username, String type, String content, Position position) {
        this.sessionId = sessionId;
        this.username = username;
        this.type = type;
        this.content = content;
        this.position = position;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "CollaborationMessage{" +
                "sessionId='" + sessionId + '\'' +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", position=" + position +
                '}';
    }

    // Static Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String sessionId;
        private String username;
        private String type;
        private String content;
        private Position position;

        public Builder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder position(Position position) {
            this.position = position;
            return this;
        }

        public CollaborationMessage build() {
            return new CollaborationMessage(sessionId, username, type, content, position);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CollaborationMessage that = (CollaborationMessage) o;

        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return position != null ? position.equals(that.position) : that.position == null;
    }

    @Override
    public int hashCode() {
        int result = sessionId != null ? sessionId.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }
}

class Position {
    private int line;
    private int column;

    public Position() {
    }

    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public String toString() {
        return "Position{" +
                "line=" + line +
                ", column=" + column +
                '}';
    }

    // Static Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int line;
        private int column;

        public Builder line(int line) {
            this.line = line;
            return this;
        }

        public Builder column(int column) {
            this.column = column;
            return this;
        }

        public Position build() {
            return new Position(line, column);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (line != position.line) return false;
        return column == position.column;
    }

    @Override
    public int hashCode() {
        int result = line;
        result = 31 * result + column;
        return result;
    }
}
