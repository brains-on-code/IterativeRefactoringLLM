package com.github.mcp.server.filesystem.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for executing shell commands and capturing their output.
 */
public final class ShellHelper {

  private static final Logger log = LoggerFactory.getLogger(ShellHelper.class);

  private static final long DEFAULT_TIMEOUT_MINUTES = 5L;

  private ShellHelper() {
    // Utility class; prevent instantiation.
  }

  /**
   * Executes a shell command and returns its output as a list of lines.
   *
   * @param command the command and its arguments to execute
   * @return a list of lines from the command's standard output; an empty list if execution fails
   */
  public static List<String> exec(String... command) {
    final String commandStatement = String.join(" ", command);
    log.info("Executing command: {}", commandStatement);

    Process process;
    try {
      process = new ProcessBuilder(command)
          .redirectErrorStream(true)
          .start();
    } catch (IOException e) {
      log.error("Failed to start command: {}", commandStatement, e);
      return List.of();
    }

    try (BufferedReader reader =
             new BufferedReader(
                 new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

      boolean finished = process.waitFor(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);
      if (!finished) {
        log.error("Command timed out after {} minutes: {}", DEFAULT_TIMEOUT_MINUTES, commandStatement);
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