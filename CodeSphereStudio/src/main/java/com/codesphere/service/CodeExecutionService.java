package com.codesphere.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.HostConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class CodeExecutionService {
    private final DockerClient dockerClient;
    private final ExecutionMetricsService metricsService;

    @Value("${code-execution.docker.timeout}")
    private int timeout;

    @Value("${code-execution.docker.max-memory}")
    private String maxMemory;

    private static final Map<String, LanguageConfig> LANGUAGE_CONFIGS = Map.of(
        "python", new LanguageConfig("python:3.9-slim", "python", "-c"),
        "javascript", new LanguageConfig("node:16-alpine", "node", "-e"),
        "java", new LanguageConfig("openjdk:17-slim", "java", "-cp", "/tmp"),
        "typescript", new LanguageConfig("node:16-alpine", "ts-node", "-e")
    );

    public ExecutionResult executeCode(String code, String language, String input) {
        LanguageConfig config = LANGUAGE_CONFIGS.get(language);
        if (config == null) {
            throw new UnsupportedOperationException("Language not supported: " + language);
        }

        String containerId = null;
        long startTime = System.currentTimeMillis();

        try {
            // Pull image if not exists
            dockerClient.pullImageCmd(config.getImage()).start().awaitCompletion();

            // Create container with security constraints
            CreateContainerResponse container = dockerClient.createContainerCmd(config.getImage())
                .withHostConfig(HostConfig.newHostConfig()
                    .withMemory(Long.parseLong(maxMemory.replace("m", "000000")))
                    .withNetworkMode("none")
                    .withSecurityOpts(List.of("no-new-privileges"))
                    .withReadonlyRootfs(true))
                .withCmd(config.buildCommand(code))
                .exec();

            containerId = container.getId();

            // Start container
            dockerClient.startContainerCmd(containerId).exec();

            // Write input if provided
            if (input != null && !input.isEmpty()) {
                dockerClient.attachContainer(containerId)
                    .withStdIn(new ByteArrayInputStream(input.getBytes()))
                    .start()
                    .awaitCompletion(timeout, TimeUnit.MILLISECONDS);
            }

            // Wait for completion
            int exitCode = dockerClient.waitContainerCmd(containerId)
                .start()
                .awaitCompletion(timeout, TimeUnit.MILLISECONDS);

            // Get logs
            String logs = dockerClient.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true)
                .exec(new LogContainerResultCallback())
                .awaitCompletion()
                .toString();

            long executionTime = System.currentTimeMillis() - startTime;
            metricsService.recordExecution(language, executionTime, exitCode == 0);

            return new ExecutionResult(logs, exitCode == 0 ? "SUCCESS" : "ERROR", exitCode, executionTime);

        } catch (Exception e) {
            return new ExecutionResult(e.getMessage(), "ERROR", -1, System.currentTimeMillis() - startTime);
        } finally {
            if (containerId != null) {
                try {
                    dockerClient.removeContainerCmd(containerId)
                        .withForce(true)
                        .exec();
                } catch (Exception e) {
                    log.error("Failed to remove container: " + containerId, e);
                }
            }
        }
    }
}
