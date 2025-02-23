package com.codesphere.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExecutionResult {
    private String output;
    private String status;
    private int exitCode;
    private long executionTime;
}
