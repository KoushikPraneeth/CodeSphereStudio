package com.codesphere.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeConversionRequest {
    private String sourceCode;
    private String sourceLanguage;
    private String targetLanguage;
}
