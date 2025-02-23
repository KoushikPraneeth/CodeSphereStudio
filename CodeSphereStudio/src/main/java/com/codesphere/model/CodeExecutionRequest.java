package com.codesphere.model;

import lombok.Data;

@Data
public class CodeExecutionRequest {
    private String code;
    private String language;
    private String input;
    private Integer timeoutSeconds;
}