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

    final String commandString = String.join(" ", commandArguments);
    LOGGER.info("Executing command: {}", commandString);

    final Process process;
    try {
      process = processBuilder.start();
    } catch (IOException exception) {
      LOGGER.error("Failed to execute command: {}", commandString, exception);
      return List.of();
    }

    try (BufferedReader outputReader =
        new BufferedReader(
            new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

      boolean finishedInTime =
          process.waitFor(COMMAND_TIMEOUT_MINUTES, TimeUnit.MINUTES);

      if (!finishedInTime) {
        LOGGER.error(
            "Command timed out after {} minutes: {}",
            COMMAND_TIMEOUT_MINUTES,
            commandString);
        process.destroy();
        return List.of();
      }

      return outputReader.lines().toList();
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();
      LOGGER.error("Interrupted while waiting for command: {}", commandString, exception);
      process.destroy();
      return List.of();
    }
  }
}