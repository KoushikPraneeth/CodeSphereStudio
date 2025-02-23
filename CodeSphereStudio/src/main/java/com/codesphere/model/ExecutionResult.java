package com.codesphere.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ExecutionResult {
    private String output;
    private String error;
    private int exitCode;
    private long executionTime;
}
