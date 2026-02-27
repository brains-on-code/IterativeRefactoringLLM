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
 * A utility class that provides helper methods for executing shell commands. This class contains
 * methods to execute system commands and capture their output.
 *
 * @author <a href="https://github.com/codeboyzhou">codeboyzhou</a>
 */
public final class ShellHelper {

  /** Logger instance for logging command execution events and errors. */
  private static final Logger LOGGER = LoggerFactory.getLogger(ShellHelper.class);

  private ShellHelper() {
    // Utility class; prevent instantiation
  }

  /**
   * Executes a shell command and returns its output as a list of strings.
   *
   * @param commandWithArguments the command and its arguments to execute
   * @return a list of strings representing the command's output
   * @throws IOException if an I/O error occurs while executing the command
   */
  public static List<String> executeCommand(String... commandWithArguments) throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder(commandWithArguments);
    processBuilder.redirectErrorStream(true);

    final String commandLine = String.join(" ", commandWithArguments);
    LOGGER.info("Executing command: {}", commandLine);

    final Process process;
    try {
      process = processBuilder.start();
    } catch (IOException exception) {
      LOGGER.error("Failed to execute command: {}", commandLine, exception);
      return List.of();
    }

    try (BufferedReader outputReader =
        new BufferedReader(
            new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

      process.waitFor(5, TimeUnit.MINUTES);
      return outputReader.lines().toList();
    } catch (InterruptedException exception) {
      Thread.currentThread().interrupt();
      LOGGER.error(
          "Interrupted while waiting for command to finish: {}", commandLine, exception);
      process.destroy();
      return List.of();
    }
  }
}