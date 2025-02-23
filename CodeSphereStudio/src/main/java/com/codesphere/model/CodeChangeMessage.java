package com.codesphere.model;

public class CodeChangeMessage {
    private String username;
    private String code;

    public CodeChangeMessage() {
    }

    public CodeChangeMessage(String username, String code) {
        this.username = username;
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CodeChangeMessage{" +
                "username='" + username + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeChangeMessage that = (CodeChangeMessage) o;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return code != null ? code.equals(that.code) : that.code == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }
}
