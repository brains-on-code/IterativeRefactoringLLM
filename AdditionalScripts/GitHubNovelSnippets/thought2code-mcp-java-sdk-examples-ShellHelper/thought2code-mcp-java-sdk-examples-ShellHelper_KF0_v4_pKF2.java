package com.github.mcp.server.filesystem.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class ShellHelper {

  private static final Logger log = LoggerFactory.getLogger(ShellHelper.class);

  private static final long DEFAULT_TIMEOUT_MINUTES = 5L;

  private ShellHelper() {
    // Prevent instantiation
  }

  public static List<String> exec(String... command) {
    final String commandStatement = String.join(" ", command);
    log.info("Executing command: {}", commandStatement);

    final Process process;
    try {
      process = new ProcessBuilder(command)
          .redirectErrorStream(true)
          .start();
    } catch (IOException e) {
      log.error("Failed to start command: {}", commandStatement, e);
      return List.of();
    }

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

      final boolean finished =
          process.waitFor(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);

      if (!finished) {
        log.error(
            "Command timed out after {} minutes: {}",
            DEFAULT_TIMEOUT_MINUTES,
            commandStatement
        );
        process.destroyForcibly();
        return List.of();
      }

      return reader.lines().toList();

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Interrupted while waiting for command: {}", commandStatement, e);
      process.destroyForcibly();
      return List.of();
    } catch (IOException e) {
      log.error("I/O error while reading output for command: {}", commandStatement, e);
      process.destroyForcibly();
      return List.of();
    }
  }
}