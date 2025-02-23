package com.codesphere.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LanguageConfig {
    private final String image;
    private final String command;
    private final String executeFlag;
    private final String workingDir;

    public LanguageConfig(String image, String command, String executeFlag) {
        this(image, command, executeFlag, "/tmp");
    }

    public String[] buildCommand(String code) {
        return new String[]{command, executeFlag, code};
    }
}