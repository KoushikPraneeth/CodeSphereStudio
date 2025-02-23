package com.codesphere.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollaborationMessage {
    private String username;
    private String content;
    private String type;
    private LocalDateTime timestamp;

    public CollaborationMessage(String username, String content) {
        this.username = username;
        this.content = content;
        this.type = "CODE_CHANGE";
        this.timestamp = LocalDateTime.now();
    }
}
