package com.codesphere.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CollaborationMessage {
    private String sessionId;
    private String username;
    private String type;
    private String content;
    private Position position;
}

@Data
@Builder
class Position {
    private int line;
    private int column;
}
