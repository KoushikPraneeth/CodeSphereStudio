package com.codesphere.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    @Bean
    public MeterRegistry meterRegistry() {
        return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    }

    @Bean
    public Counter codeExecutionCounter(MeterRegistry registry) {
        return Counter.builder("code.executions")
            .description("Number of code executions")
            .tag("type", "total")
            .register(registry);
    }

    @Bean
    public Timer codeExecutionTimer(MeterRegistry registry) {
        return Timer.builder("code.execution.time")
            .description("Time taken for code execution")
            .register(registry);
    }

    @Bean
    public Counter codeConversionCounter(MeterRegistry registry) {
        return Counter.builder("code.conversions")
            .description("Number of code conversions")
            .tag("type", "total")
            .register(registry);
    }
}
