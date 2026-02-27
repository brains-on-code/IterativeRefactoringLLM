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

public final class Class1 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Class1.class);
  private static final long COMMAND_TIMEOUT_MINUTES = 5L;

  private Class1() {
    // Utility class
  }

  public static List<String> method1(String... command) {
    if (isInvalidCommand(command)) {
      LOGGER.warn("No command provided to execute.");
      return Collections.emptyList();
    }

    String commandString = String.join(" ", command);
    ProcessBuilder processBuilder = createProcessBuilder(command);

    LOGGER.info("Executing command: {}", commandString);

    Process process = startProcess(processBuilder, commandString);
    if (process == null) {
      return Collections.emptyList();
    }

    try (BufferedReader reader = createProcessOutputReader(process)) {
      if (!waitForProcess(process, commandString)) {
        return Collections.emptyList();
      }
      return reader.lines().toList();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Interrupted while waiting for command: {}", commandString, e);
      process.destroyForcibly();
      return Collections.emptyList();
    } catch (IOException e) {
      LOGGER.error("I/O error while reading output for command: {}", commandString, e);
      process.destroyForcibly();
      return Collections.emptyList();
    }
  }

  private static boolean isInvalidCommand(String[] command) {
    return command == null || command.length == 0;
  }

  private static Process startProcess(ProcessBuilder processBuilder, String commandString) {
    try {
      return processBuilder.start();
    } catch (IOException e) {
      LOGGER.error("Failed to execute command: {}", commandString, e);
      return null;
    }
  }

  private static ProcessBuilder createProcessBuilder(String... command) {
    return new ProcessBuilder(command)
        .redirectErrorStream(true);
  }

  private static BufferedReader createProcessOutputReader(Process process) {
    return new BufferedReader(
        new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
    );
  }

  private static boolean waitForProcess(Process process, String commandString)
      throws InterruptedException {
    boolean finished = process.waitFor(COMMAND_TIMEOUT_MINUTES, TimeUnit.MINUTES);
    if (!finished) {
      LOGGER.error(
          "Command timed out after {} minutes: {}",
          COMMAND_TIMEOUT_MINUTES,
          commandString
      );
      process.destroyForcibly();
    }
    return finished;
  }
}