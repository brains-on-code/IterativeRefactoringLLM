package com.github.mcp.server.filesystem.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CommandExecutor {

  private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutor.class);
  private static final long COMMAND_TIMEOUT_MINUTES = 5L;

  private CommandExecutor() {
    // Utility class; prevent instantiation
  }

  public static List<String> executeCommand(String... command) throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder(command);
    processBuilder.redirectErrorStream(true);

    final String commandAsString = String.join(" ", command);
    LOGGER.info("Executing command: {}", commandAsString);

    final Process process;
    try {
      process = processBuilder.start();
    } catch (IOException exception) {
      LOGGER.error("Failed to execute command: {}", commandAsString, exception);
      return List.of();
    }

    try (BufferedReader outputReader =
        new BufferedReader(
            new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

      boolean completed =
          process.waitFor(COMMAND_TIMEOUT_MINUTES, TimeUnit.MINUTES);

      if (!completed) {
        LOGGER.error(
            "Command did not complete within {} minutes: {}",
            COMMAND_TIMEOUT_MINUTES,
            commandAsString);
        process.destroy();
        return List.of();
      }

      return outputReader.lines().toList();
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();
      LOGGER.error("Interrupted while waiting for command to complete: {}", commandAsString, exception);
      process.destroy();
      return List.of();
    }
  }
}