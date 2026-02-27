package com.github.mcp.server.filesystem.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class that provides helper methods for executing shell commands. This class contains
 * methods to execute system commands and capture their output.
 *
 * @author <a href="https://github.com/codeboyzhou">codeboyzhou</a>
 */
public final class ShellHelper {

  private static final Logger log = LoggerFactory.getLogger(ShellHelper.class);
  private static final long DEFAULT_TIMEOUT_MINUTES = 5L;

  private ShellHelper() {
    // Utility class; prevent instantiation
  }

  /**
   * Executes a shell command and returns its output as a list of strings.
   *
   * @param command the command and its arguments to execute
   * @return a list of strings representing the command's output, or an empty list if execution
   *     fails
   * @throws IOException if an I/O error occurs while executing the command
   */
  public static List<String> exec(String... command) throws IOException {
    if (command == null || command.length == 0) {
      log.warn("No command provided to execute.");
      return Collections.emptyList();
    }

    final String commandStatement = String.join(" ", command);
    log.info("Executing command: {}", commandStatement);

    ProcessBuilder processBuilder = new ProcessBuilder(command).redirectErrorStream(true);

    final Process process;
    try {
      process = processBuilder.start();
    } catch (IOException e) {
      log.error("Failed to execute command: {}", commandStatement, e);
      return Collections.emptyList();
    }

    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

      boolean finished = process.waitFor(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);
      if (!finished) {
        log.error(
            "Command timed out after {} minutes: {}", DEFAULT_TIMEOUT_MINUTES, commandStatement);
        process.destroyForcibly();
        return Collections.emptyList();
      }

      return reader.lines().toList();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("Interrupted while waiting for command to finish: {}", commandStatement, e);
      process.destroyForcibly();
      return Collections.emptyList();
    }
  }
}