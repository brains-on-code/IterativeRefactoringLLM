package com.github.mcp.server.filesystem.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CommandExecutor {

  private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutor.class);
  private static final long COMMAND_TIMEOUT_MINUTES = 5L;

  private CommandExecutor() {
    // Utility class; prevent instantiation
  }

  public static List<String> executeCommand(String... commandArguments) throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder(commandArguments);
    processBuilder.redirectErrorStream(true);

    final String commandAsString = String.join(" ", commandArguments);
    LOGGER.info("Executing command: {}", commandAsString);

    final Process commandProcess;
    try {
      commandProcess = processBuilder.start();
    } catch (IOException exceptionWhileStartingProcess) {
      LOGGER.error("Failed to execute command: {}", commandAsString, exceptionWhileStartingProcess);
      return List.of();
    }

    try (BufferedReader commandOutputReader =
        new BufferedReader(
            new InputStreamReader(commandProcess.getInputStream(), StandardCharsets.UTF_8))) {

      boolean commandCompletedWithinTimeout =
          commandProcess.waitFor(COMMAND_TIMEOUT_MINUTES, TimeUnit.MINUTES);

      if (!commandCompletedWithinTimeout) {
        LOGGER.error(
            "Command did not complete within {} minutes: {}",
            COMMAND_TIMEOUT_MINUTES,
            commandAsString);
        commandProcess.destroy();
        return List.of();
      }

      return commandOutputReader.lines().toList();
    } catch (InterruptedException exceptionWhileWaitingForProcess) {
      Thread.currentThread().interrupt();
      LOGGER.error(
          "Interrupted while waiting for command to complete: {}",
          commandAsString,
          exceptionWhileWaitingForProcess);
      commandProcess.destroy();
      return List.of();
    }
  }
}