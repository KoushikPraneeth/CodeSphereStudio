package com.codesphere.model;

public class ExecutionResult {
    private String output;
    private String error;
    private int exitCode;
    private long executionTime;

    public ExecutionResult() {
    }

    public ExecutionResult(String output, String error, int exitCode, long executionTime) {
        this.output = output;
        this.error = error;
        this.exitCode = exitCode;
        this.executionTime = executionTime;
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

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public String toString() {
        return "ExecutionResult{" +
                "output='" + output + '\'' +
                ", error='" + error + '\'' +
                ", exitCode=" + exitCode +
                ", executionTime=" + executionTime +
                '}';
    }

    // Static Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String output;
        private String error;
        private int exitCode;
        private long executionTime;

        public Builder output(String output) {
            this.output = output;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder exitCode(int exitCode) {
            this.exitCode = exitCode;
            return this;
        }

        public Builder executionTime(long executionTime) {
            this.executionTime = executionTime;
            return this;
        }

        public ExecutionResult build() {
            return new ExecutionResult(output, error, exitCode, executionTime);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExecutionResult that = (ExecutionResult) o;

        if (exitCode != that.exitCode) return false;
        if (executionTime != that.executionTime) return false;
        if (output != null ? !output.equals(that.output) : that.output != null) return false;
        return error != null ? error.equals(that.error) : that.error == null;
    }

    @Override
    public int hashCode() {
        int result = output != null ? output.hashCode() : 0;
        result = 31 * result + (error != null ? error.hashCode() : 0);
        result = 31 * result + exitCode;
        result = 31 * result + (int) (executionTime ^ (executionTime >>> 32));
        return result;
    }
}
