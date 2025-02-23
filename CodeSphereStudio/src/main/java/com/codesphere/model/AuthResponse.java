package com.codesphere.model;

public class AuthResponse {
    private String token;
    private String username;

    public AuthResponse() {
    }

    public AuthResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='[PROTECTED]'" +
                ", username='" + username + '\'' +
                '}';
    }

    // Static Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private String username;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(token, username);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthResponse that = (AuthResponse) o;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}
