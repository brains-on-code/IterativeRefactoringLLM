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
 * Executes system commands and returns their standard output.
 */
public final class CommandExecutor {

  private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutor.class);
  private static final long DEFAULT_TIMEOUT_MINUTES = 5L;

  private CommandExecutor() {
    // Prevent instantiation.
  }

  /**
   * Executes the given command and returns all lines from its standard output.
   *
   * <p>The process is given up to {@value #DEFAULT_TIMEOUT_MINUTES} minutes to complete.
   * If the process fails to start, times out, or is interrupted, an empty list is returned
   * and the error is logged.</p>
   *
   * @param command the command and its arguments
   * @return a list of output lines from the command, or an empty list on error
   */
  public static List<String> execute(String... command) {
    if (command == null || command.length == 0) {
      LOGGER.warn("No command provided to execute.");
      return List.of();
    }

    final String commandString = String.join(" ", command);
    LOGGER.info("Executing command: {}", commandString);

    ProcessBuilder processBuilder = new ProcessBuilder(command)
        .redirectErrorStream(true);

    final Process process;
    try {
      process = processBuilder.start();
    } catch (IOException e) {
      LOGGER.error("Failed to execute command: {}", commandString, e);
      return List.of();
    }

    try (BufferedReader reader =
             new BufferedReader(
                 new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

      boolean finished = process.waitFor(DEFAULT_TIMEOUT_MINUTES, TimeUnit.MINUTES);

      if (!finished) {
        LOGGER.error(
            "Command timed out after {} minutes: {}",
            DEFAULT_TIMEOUT_MINUTES,
            commandString);
        process.destroyForcibly();
        return List.of();
      }

      return reader.lines().toList();

    } catch (InterruptedException e) {
      LOGGER.error("Interrupted while waiting for command: {}", commandString, e);
      Thread.currentThread().interrupt();
      process.destroyForcibly();
      return List.of();
    } catch (Exception e) {
      LOGGER.error("Unexpected error while executing command: {}", commandString, e);
      process.destroyForcibly();
      return List.of();
    }
  }
}