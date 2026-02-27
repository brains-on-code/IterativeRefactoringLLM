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
      try (BufferedReader bufferedReader =
               new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        return bufferedReader.lines().collect(joining(LINE_SEPARATOR));
      }
    }
  }

  public static String readAsString(Path filepath) throws IOException {
    return String.join(LINE_SEPARATOR, Files.readAllLines(filepath));
  }

  public static List<String> fuzzySearch(String start, String filename) throws IOException {
    if (IS_WINDOWS) {
      return ShellHelper.exec(
          "cmd.exe", "/c", "dir", start, "/s", "/b", "|", "findstr", filename);
    }
    return ShellHelper.exec("sh", "-c", "find", start, "-name", filename);
  }

  public static List<String> listDirectory(String dir) throws IOException {
    try (Stream<Path> stream = Files.list(Path.of(dir))) {
      return stream
          .map(Path::toAbsolutePath)
          .map(Path::toString)
          .toList();
    }
  }
}