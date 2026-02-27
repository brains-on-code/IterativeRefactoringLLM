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

/**
 * Utility class for executing shell commands and capturing their output.
 */
public final class ShellHelper {

  private static final Logger LOG = LoggerFactory.getLogger(ShellHelper.class);
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
      LOG.warn("No command provided to execute.");
      return Collections.emptyList();
    }

    final String commandStatement = String.join(" ", command);
    LOG.info("Executing command: {}", commandStatement);

    final Process process = startProcess(command, commandStatement);
    if (process == null) {
      return Collections.emptyList();
    }

    return readProcessOutput(process, commandStatement);
  }

  private static Process startProcess(String[] command, String commandStatement) {
    try {
      return new ProcessBuilder(command)
          .redirectErrorStream(true)
          .start();
    } catch (IOException e) {
      LOG.error("Failed to execute command: {}", commandStatement, e);
      return null;
    }
  }

  private static List<String> readProcessOutput(Process process, String commandStatement)
      throws IOException {

    if (!waitForProcess(process, commandStatement)) {
      return Collections.emptyList();
    }

    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
      return reader.lines().toList();
    }
  }

  private static boolean waitForProcess(Process process, String commandStatement) {
    try {
      final boolean finished = process.waitFor(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);
      if (!finished) {
        LOG.error(
            "Command timed out after {} minutes: {}", DEFAULT_TIMEOUT_MINUTES, commandStatement);
        process.destroyForcibly();
      }
      return finished;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOG.error("Interrupted while waiting for command to finish: {}", commandStatement, e);
      process.destroyForcibly();
      return false;
    }
  }
}