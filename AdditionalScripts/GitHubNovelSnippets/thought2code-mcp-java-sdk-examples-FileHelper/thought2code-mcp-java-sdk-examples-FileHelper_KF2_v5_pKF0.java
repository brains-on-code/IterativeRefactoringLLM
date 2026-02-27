package com.github.mcp.server.filesystem.common;

import static java.util.stream.Collectors.joining;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public final class FileHelper {

  private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
  private static final boolean IS_WINDOWS = OS_NAME.contains("win");
  private static final String LINE_SEPARATOR = System.lineSeparator();

  private FileHelper() {
    // Utility class; prevent instantiation
  }

  public static String readResourceAsString(String filename) throws IOException {
    ClassLoader classLoader = FileHelper.class.getClassLoader();

    try (InputStream inputStream = classLoader.getResourceAsStream(filename)) {
      if (inputStream == null) {
        throw new NoSuchFileException(filename);
      }

      try (BufferedReader reader =
               new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        return reader.lines().collect(joining(LINE_SEPARATOR));
      }
    }
  }

  public static String readAsString(Path filepath) throws IOException {
    return Files.readString(filepath, StandardCharsets.UTF_8);
  }

  public static List<String> fuzzySearch(String startDirectory, String filename) throws IOException {
    return IS_WINDOWS
        ? ShellHelper.exec(
            "cmd.exe", "/c", "dir", startDirectory, "/s", "/b", "|", "findstr", filename)
        : ShellHelper.exec("sh", "-c", "find", startDirectory, "-name", filename);
  }

  public static List<String> listDirectory(String directory) throws IOException {
    Path directoryPath = Path.of(directory);

    try (Stream<Path> stream = Files.list(directoryPath)) {
      return stream
          .map(Path::toAbsolutePath)
          .map(Path::toString)
          .toList();
    }
  }
}