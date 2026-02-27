package com.github.mcp.server.filesystem.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class ShellHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ShellHelper.class);
  private static final long DEFAULT_TIMEOUT_MINUTES = 5L;

  private ShellHelper() {
    // Utility class; prevent instantiation
  }

  public static List<String> exec(String... command) throws IOException {
    if (command == null || command.length == 0) {
      LOGGER.warn("No command provided to execute.");
      return Collections.emptyList();
    }

    final String commandStatement = String.join(" ", command);
    LOGGER.info("Executing command: {}", commandStatement);

    final Process process = startProcess(commandStatement, command);
    if (process == null) {
      return Collections.emptyList();
    }

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

      if (!waitForProcess(process, commandStatement)) {
        return Collections.emptyList();
      }

      return reader.lines().toList();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Interrupted while waiting for command to finish: {}", commandStatement, e);
      process.destroyForcibly();
      return Collections.emptyList();
    }
  }

  private static Process startProcess(String commandStatement, String... command) {
    try {
      return new ProcessBuilder(command)
          .redirectErrorStream(true)
          .start();
    } catch (IOException e) {
      LOGGER.error("Failed to execute command: {}", commandStatement, e);
      return null;
    }
  }

  private static boolean waitForProcess(Process process, String commandStatement)
      throws InterruptedException {
    boolean finished = process.waitFor(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);
    if (!finished) {
      LOGGER.error(
          "Command timed out after {} minutes: {}",
          DEFAULT_TIMEOUT_MINUTES,
          commandStatement
      );
      process.destroyForcibly();
    }
    return finished;
  }
}