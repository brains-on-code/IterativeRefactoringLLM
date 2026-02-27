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

    final String commandString = String.join(" ", command);
    LOGGER.info("Executing command: {}", commandString);

    final Process process;
    try {
      process = processBuilder.start();
    } catch (IOException startException) {
      LOGGER.error("Failed to execute command: {}", commandString, startException);
      return List.of();
    }

    try (BufferedReader outputReader =
        new BufferedReader(
            new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

      boolean completedInTime =
          process.waitFor(COMMAND_TIMEOUT_MINUTES, TimeUnit.MINUTES);

      if (!completedInTime) {
        LOGGER.error(
            "Command did not complete within {} minutes: {}",
            COMMAND_TIMEOUT_MINUTES,
            commandString);
        process.destroy();
        return List.of();
      }

      return outputReader.lines().toList();
    } catch (InterruptedException waitException) {
      Thread.currentThread().interrupt();
      LOGGER.error(
          "Interrupted while waiting for command to complete: {}",
          commandString,
          waitException);
      process.destroy();
      return List.of();
    }
  }
}