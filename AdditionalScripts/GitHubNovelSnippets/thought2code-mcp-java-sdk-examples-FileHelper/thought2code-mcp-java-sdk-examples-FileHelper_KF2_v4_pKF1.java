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

  private FileHelper() {
    // Utility class; prevent instantiation
  }

  public static String readResourceAsString(String resourcePath) throws IOException {
    ClassLoader classLoader = FileHelper.class.getClassLoader();
    try (InputStream resourceStream = classLoader.getResourceAsStream(resourcePath)) {
      if (resourceStream == null) {
        throw new NoSuchFileException(resourcePath);
      }
      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(resourceStream, StandardCharsets.UTF_8))) {
        return reader.lines().collect(joining(System.lineSeparator()));
      }
    }
  }

  public static String readFileAsString(Path filePath) throws IOException {
    return String.join(System.lineSeparator(), Files.readAllLines(filePath));
  }

  public static List<String> fuzzySearch(String rootDirectory, String fileNamePattern)
      throws IOException {
    final boolean isWindows =
        System.getProperty("os.name").toLowerCase().contains("win");
    if (isWindows) {
      return ShellHelper.exec(
          "cmd.exe", "/c", "dir", rootDirectory, "/s", "/b", "|", "findstr", fileNamePattern);
    }
    return ShellHelper.exec("sh", "-c", "find", rootDirectory, "-name", fileNamePattern);
  }

  public static List<String> listDirectory(String directoryPath) throws IOException {
    try (Stream<Path> pathStream = Files.list(Path.of(directoryPath))) {
      return pathStream
          .map(Path::toAbsolutePath)
          .map(Path::toString)
          .toList();
    }
  }
}