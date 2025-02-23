@Configuration
@EnablePrometheusMetrics
public class MetricsConfig {
    @Bean
    MeterRegistry meterRegistry() {
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