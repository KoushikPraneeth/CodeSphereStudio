package com.codesphere.controller;

import com.codesphere.model.CodeConversionRequest;
import com.codesphere.service.AICodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {
    private final AICodeService aiCodeService;

    @PostMapping(value = "/convert", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> convertCode(@RequestBody CodeConversionRequest request) {
        return aiCodeService.convertCode(
            request.getSourceCode(),
            request.getSourceLanguage(),
            request.getTargetLanguage()
        );
    }
}
