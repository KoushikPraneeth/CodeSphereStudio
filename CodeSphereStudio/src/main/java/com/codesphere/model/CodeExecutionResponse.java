package com.codesphere.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodeExecutionResponse {
    private String output;
    private String error;
    private Long executionTime;
    private String status;
}