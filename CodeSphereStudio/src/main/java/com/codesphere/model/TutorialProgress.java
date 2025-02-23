package com.codesphere.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TutorialProgress {
    private boolean passed;
    private String output;
    private String message;
}