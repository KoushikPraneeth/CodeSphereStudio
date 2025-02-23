package com.codesphere.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConversionCacheService {
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public String getConversion(String sourceCode, String sourceLanguage, String targetLanguage) {
        String key = generateKey(sourceCode, sourceLanguage, targetLanguage);
        return cache.get(key);
    }

    public void cacheConversion(String sourceCode, String sourceLanguage, String targetLanguage, String result) {
        String key = generateKey(sourceCode, sourceLanguage, targetLanguage);
        cache.put(key, result);
    }

    private String generateKey(String sourceCode, String sourceLanguage, String targetLanguage) {
        return String.format("%s:%s:%s", sourceLanguage, targetLanguage, sourceCode.hashCode());
    }
}