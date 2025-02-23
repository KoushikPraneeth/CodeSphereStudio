package com.codesphere.model;

public class CodeExecutionRequest {
    private String code;
    private String language;
    private String input;
    private Integer timeoutSeconds;

    public CodeExecutionRequest() {
    }

    public CodeExecutionRequest(String code, String language, String input, Integer timeoutSeconds) {
        this.code = code;
        this.language = language;
        this.input = input;
        this.timeoutSeconds = timeoutSeconds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(Integer timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    @Override
    public String toString() {
        return "CodeExecutionRequest{" +
                "code='" + code + '\'' +
                ", language='" + language + '\'' +
                ", input='" + input + '\'' +
                ", timeoutSeconds=" + timeoutSeconds +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeExecutionRequest that = (CodeExecutionRequest) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (input != null ? !input.equals(that.input) : that.input != null) return false;
        return timeoutSeconds != null ? timeoutSeconds.equals(that.timeoutSeconds) : that.timeoutSeconds == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (input != null ? input.hashCode() : 0);
        result = 31 * result + (timeoutSeconds != null ? timeoutSeconds.hashCode() : 0);
        return result;
    }
}
