package com.codesphere.controller;

import com.codesphere.model.CodeExecutionRequest;
import com.codesphere.model.CodeExecutionResponse;
import com.codesphere.service.CodeExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeExecutionController {
    
    private final CodeExecutionService codeExecutionService;
    
    @PostMapping("/execute")
    public ResponseEntity<CodeExecutionResponse> executeCode(@RequestBody CodeExecutionRequest request) {
        var result = codeExecutionService.executeCode(request);
        CodeExecutionResponse response = CodeExecutionResponse.builder()
            .output(result.getOutput())
            .error(result.getError())
            .executionTime(result.getExecutionTime())
            .status(result.getExitCode() == 0 ? "SUCCESS" : "ERROR")
            .build();
        return ResponseEntity.ok(response);
    }
}
