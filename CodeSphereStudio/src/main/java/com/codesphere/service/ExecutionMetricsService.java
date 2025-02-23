package com.codesphere.service;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class ExecutionMetricsService {
    private final MeterRegistry meterRegistry;

    public ExecutionMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void recordExecution(String language, long executionTime, boolean success) {
        meterRegistry.counter("code.executions", "language", language, "status", success ? "success" : "failure")
            .increment();
        meterRegistry.timer("code.execution.time", "language", language)
            .record(() -> {});
    }
}
