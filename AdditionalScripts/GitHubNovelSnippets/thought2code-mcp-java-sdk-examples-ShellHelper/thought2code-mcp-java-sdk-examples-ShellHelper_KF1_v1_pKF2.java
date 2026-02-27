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
 * Utility class for executing system commands and capturing their output.
 */
public final class Class1 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Class1.class);

  private Class1() {
    // Utility class; prevent instantiation.
  }

  /**
   * Executes the given command and returns all lines from its standard output.
   *
   * <p>The process is given up to 5 minutes to complete. If the process fails to start or is
   * interrupted while waiting, an empty list is returned and the error is logged.</p>
   *
   * @param command the command and its arguments
   * @return a list of output lines from the command, or an empty list on error
   * @throws IOException if an I/O error occurs while starting the process
   */
  public static List<String> method1(String... command) throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder(command);
    processBuilder.redirectErrorStream(true);

    final String commandString = String.join(" ", command);
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

      process.waitFor(5, TimeUnit.MINUTES);
      return reader.lines().toList();
    } catch (InterruptedException e) {
      LOGGER.error("Interrupted while waiting for command: {}", commandString, e);
      process.destroy();
      Thread.currentThread().interrupt();
      return List.of();
    }
  }
}