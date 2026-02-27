package com.github.mcp.server.filesystem.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ShellHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShellHelper.class);
  private static final long COMMAND_TIMEOUT_MINUTES = 5L;

  private ShellHelper() {
    // Utility class; prevent instantiation
  }

  public static List<String> executeCommand(String... commandArguments) throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder(commandArguments);
    processBuilder.redirectErrorStream(true);

    final String commandDescription = String.join(" ", commandArguments);
    LOGGER.info("Executing command: {}", commandDescription);

    final Process process;
    try {
      process = processBuilder.start();
    } catch (IOException exception) {
      LOGGER.error("Failed to execute command: {}", commandDescription, exception);
      return List.of();
    }

    try (BufferedReader processOutputReader =
        new BufferedReader(
            new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

      boolean completedWithinTimeout =
          process.waitFor(COMMAND_TIMEOUT_MINUTES, TimeUnit.MINUTES);

      if (!completedWithinTimeout) {
        LOGGER.error(
            "Command timed out after {} minutes: {}",
            COMMAND_TIMEOUT_MINUTES,
            commandDescription);
        process.destroy();
        return List.of();
      }

      return processOutputReader.lines().toList();
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();
      LOGGER.error("Interrupted while waiting for command: {}", commandDescription, exception);
      process.destroy();
      return List.of();
    }
  }
}