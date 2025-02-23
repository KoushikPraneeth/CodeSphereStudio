package com.codesphere.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageConfig {
    private String image;
    private String command;
    private String fileExtension;
    private String compileCommand;
}
