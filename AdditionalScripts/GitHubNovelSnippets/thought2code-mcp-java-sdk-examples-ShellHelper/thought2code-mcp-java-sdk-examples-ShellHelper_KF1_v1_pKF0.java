package com.github.mcp.server.filesystem.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class Class1 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Class1.class);
  private static final long COMMAND_TIMEOUT_MINUTES = 5L;

  private Class1() {
    // Utility class
  }

  public static List<String> method1(String... command) throws IOException {
    if (command == null || command.length == 0) {
      LOGGER.warn("No command provided to execute.");
      return List.of();
    }

    ProcessBuilder processBuilder = new ProcessBuilder(command);
    processBuilder.redirectErrorStream(true);

    String commandString = String.join(" ", command);
    LOGGER.info("Executing command: {}", commandString);

    Process process;
    try {
      process = processBuilder.start();
    } catch (IOException e) {
      LOGGER.error("Failed to execute command: {}", commandString, e);
      return List.of();
    }

    try (BufferedReader reader =
             new BufferedReader(
                 new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

      boolean finished =
          process.waitFor(COMMAND_TIMEOUT_MINUTES, TimeUnit.MINUTES);

      if (!finished) {
        LOGGER.error("Command timed out after {} minutes: {}", COMMAND_TIMEOUT_MINUTES, commandString);
        process.destroyForcibly();
        return List.of();
      }

      return reader.lines().toList();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Interrupted while waiting for command: {}", commandString, e);
      process.destroyForcibly();
      return List.of();
    }
  }
}