package com.codesphere.service;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecutionMetricsService {
    private final MeterRegistry meterRegistry;

    public void recordExecution(String language, long executionTime, boolean success) {
        meterRegistry.counter("code.executions", "language", language, "status", success ? "success" : "failure")
            .increment();
        meterRegistry.timer("code.execution.time", "language", language)
            .record(() -> {});
    }
}