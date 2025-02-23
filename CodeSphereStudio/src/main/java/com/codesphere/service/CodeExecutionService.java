package com.codesphere.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.command.WaitContainerCmd;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.WaitResponse;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import com.codesphere.model.ExecutionResult;
import com.codesphere.model.LanguageConfig;
import com.codesphere.model.CodeExecutionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@Service
public class CodeExecutionService {
    private static final Logger log = LoggerFactory.getLogger(CodeExecutionService.class);
    private final DockerClient dockerClient;
    private final ExecutionMetricsService metricsService;

    @Value("${code-execution.docker.timeout}")
    private int timeout;

    @Value("${code-execution.docker.max-memory}")
    private String maxMemory;

    private static final Map<String, LanguageConfig> LANGUAGE_CONFIGS = Map.of(
        "python", LanguageConfig.builder().image("python:3.9-slim").command("python").fileExtension("-c").build(),
        "javascript", LanguageConfig.builder().image("node:16-alpine").command("node").fileExtension("-e").build(),
        "java", LanguageConfig.builder().image("openjdk:17-slim").command("java").fileExtension("-cp").compileCommand("/tmp").build(),
        "typescript", LanguageConfig.builder().image("node:16-alpine").command("ts-node").fileExtension("-e").build()
    );

    public CodeExecutionService(DockerClient dockerClient, ExecutionMetricsService metricsService) {
        this.dockerClient = dockerClient;
        this.metricsService = metricsService;
    }

    public ExecutionResult executeCode(CodeExecutionRequest request) {
        return executeCode(request.getCode(), request.getLanguage(), request.getInput());
    }

    public ExecutionResult executeCode(String code, String language, String input) {
        LanguageConfig config = LANGUAGE_CONFIGS.get(language);
        if (config == null) {
            throw new UnsupportedOperationException("Language not supported: " + language);
        }

        String containerId = null;
        long startTime = System.currentTimeMillis();

        try {
            dockerClient.pullImageCmd(config.getImage()).start().awaitCompletion();

            CreateContainerResponse container = dockerClient.createContainerCmd(config.getImage())
                .withHostConfig(HostConfig.newHostConfig()
                    .withMemory(Long.parseLong(maxMemory.replace("m", "000000")))
                    .withNetworkMode("none")
                    .withSecurityOpts(List.of("no-new-privileges"))
                    .withReadonlyRootfs(true))
                .withCmd(config.getCommand(), config.getFileExtension(), code)
                .exec();

            containerId = container.getId();
            dockerClient.startContainerCmd(containerId).exec();

            if (input != null && !input.isEmpty()) {
                try (var inputStream = new ByteArrayInputStream(input.getBytes())) {
                    dockerClient.copyArchiveToContainerCmd(containerId)
                        .withTarInputStream(inputStream)
                        .withRemotePath("/tmp")
                        .exec();
                }
            }

            WaitContainerResultCallback waitCallback = new WaitContainerResultCallback();
            int exitCode = dockerClient.waitContainerCmd(containerId)
                .exec(waitCallback)
                .awaitStatusCode(timeout, java.util.concurrent.TimeUnit.MILLISECONDS);


            LogContainerCmd logCmd = dockerClient.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true);
            LogContainerResultCallback callback = new LogContainerResultCallback();
            logCmd.exec(callback).awaitCompletion();
            String logs = callback.getLog();

            long executionTime = System.currentTimeMillis() - startTime;
            metricsService.recordExecution(language, executionTime, exitCode == 0);

            return ExecutionResult.builder()
                .output(logs)
                .error(exitCode != 0 ? "Execution failed" : "")
                .exitCode(exitCode)
                .executionTime(executionTime)
                .build();

        } catch (Exception e) {
            log.error("Execution failed", e);
            return ExecutionResult.builder()
                .output("")
                .error(e.getMessage())
                .exitCode(-1)
                .executionTime(System.currentTimeMillis() - startTime)
                .build();
        } finally {
            if (containerId != null) {
                try {
                    dockerClient.removeContainerCmd(containerId).withForce(true).exec();
                } catch (Exception e) {
                    log.error("Failed to remove container: " + containerId, e);
                }
            }
        }
    }

    private static class LogContainerResultCallback extends com.github.dockerjava.api.async.ResultCallback.Adapter<Frame> {
        private final StringBuilder log = new StringBuilder();

        @Override
        public void onNext(Frame frame) {
            log.append(new String(frame.getPayload()));
        }

        public String getLog() {
            return log.toString();
        }
    }
}
