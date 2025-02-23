package com.codesphere.model;

public class CodeExecutionResponse {
    private String output;
    private String error;
    private Long executionTime;
    private String status;

    public CodeExecutionResponse() {
    }

    public CodeExecutionResponse(String output, String error, Long executionTime, String status) {
        this.output = output;
        this.error = error;
        this.executionTime = executionTime;
        this.status = status;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CodeExecutionResponse{" +
                "output='" + output + '\'' +
                ", error='" + error + '\'' +
                ", executionTime=" + executionTime +
                ", status='" + status + '\'' +
                '}';
    }

    // Static Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String output;
        private String error;
        private Long executionTime;
        private String status;

        public Builder output(String output) {
            this.output = output;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder executionTime(Long executionTime) {
            this.executionTime = executionTime;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public CodeExecutionResponse build() {
            return new CodeExecutionResponse(output, error, executionTime, status);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeExecutionResponse that = (CodeExecutionResponse) o;

        if (output != null ? !output.equals(that.output) : that.output != null) return false;
        if (error != null ? !error.equals(that.error) : that.error != null) return false;
        if (executionTime != null ? !executionTime.equals(that.executionTime) : that.executionTime != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        int result = output != null ? output.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + (executionTime != null ? executionTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
