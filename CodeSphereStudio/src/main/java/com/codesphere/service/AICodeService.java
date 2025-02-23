package com.codesphere.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AICodeService {
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;
    private final ConversionCacheService cacheService;

    @Value("${ai.groq.api-key}")
    private String apiKey;

    @Value("${ai.groq.api-url}")
    private String apiUrl;

    public Flux<String> convertCode(String sourceCode, String sourceLanguage, String targetLanguage) {
        // Check cache first
        String cachedResult = cacheService.getConversion(sourceCode, sourceLanguage, targetLanguage);
        if (cachedResult != null) {
            return Flux.just(cachedResult);
        }

        String systemPrompt = buildSystemPrompt(sourceLanguage, targetLanguage);
        Map<String, Object> requestBody = buildRequestBody(systemPrompt, sourceCode);

        return webClientBuilder.build()
            .post()
            .uri(apiUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToFlux(String.class)
            .map(this::extractContent)
            .doOnComplete(() -> cacheConversion(sourceCode, sourceLanguage, targetLanguage));
    }

    private String buildSystemPrompt(String sourceLanguage, String targetLanguage) {
        return String.format("""
            You are an expert programmer. Convert the provided %s code to %s.
            Follow these rules:
            1. Maintain the same functionality
            2. Use idiomatic %s patterns
            3. Preserve comments and documentation
            4. Include error handling
            5. Only output the converted code without explanations
            """, sourceLanguage, targetLanguage, targetLanguage);
    }

    private Map<String, Object> buildRequestBody(String systemPrompt, String sourceCode) {
        return Map.of(
            "messages", List.of(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user", "content", sourceCode)
            ),
            "model", "deepseek-r1-distill-llama-70b",
            "temperature", 0.6,
            "max_completion_tokens", 4096,
            "top_p", 0.95,
            "stream", true
        );
    }

    public Mono<String> explainCode(String sourceCode, String language) {
        String prompt = String.format(
            "Explain this %s code concisely, focusing on its main functionality and key components:",
            language
        );

        Map<String, Object> systemMessage = Map.of(
            "role", "system",
            "content", "You are an expert programmer providing concise code explanations."
        );

        Map<String, Object> userMessage = Map.of(
            "role", "user",
            "content", prompt + "\n\n" + sourceCode
        );

        Map<String, Object> requestBody = Map.of(
            "messages", List.of(systemMessage, userMessage),
            "model", "deepseek-r1-distill-llama-70b",
            "temperature", 0.7,
            "max_completion_tokens", 1000,
            "stream", false
        );

        return webClientBuilder.build()
            .post()
            .uri(apiUrl)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .map(this::extractContent);
    }

    private String extractContent(String response) {
        try {
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            Map<String, Object> choice = ((List<Map<String, Object>>) responseMap.get("choices")).get(0);
            Map<String, Object> delta = (Map<String, Object>) choice.get("delta");
            return (String) delta.get("content");
        } catch (Exception e) {
            return "";
        }
    }
}
