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
    try (InputStream resourceInputStream = classLoader.getResourceAsStream(resourcePath)) {
      if (resourceInputStream == null) {
        throw new NoSuchFileException(resourcePath);
      }
      try (BufferedReader bufferedReader =
          new BufferedReader(new InputStreamReader(resourceInputStream, StandardCharsets.UTF_8))) {
        return bufferedReader.lines().collect(joining(System.lineSeparator()));
      }
    }
  }

  public static String readFileAsString(Path filePath) throws IOException {
    return String.join(System.lineSeparator(), Files.readAllLines(filePath));
  }

  public static List<String> fuzzySearch(String rootDirectoryPath, String fileNamePattern)
      throws IOException {
    final boolean isWindowsOs =
        System.getProperty("os.name").toLowerCase().contains("win");
    if (isWindowsOs) {
      return ShellHelper.exec(
          "cmd.exe", "/c", "dir", rootDirectoryPath, "/s", "/b", "|", "findstr", fileNamePattern);
    }
    return ShellHelper.exec("sh", "-c", "find", rootDirectoryPath, "-name", fileNamePattern);
  }

  public static List<String> listDirectory(String directoryPath) throws IOException {
    try (Stream<Path> directoryStream = Files.list(Path.of(directoryPath))) {
      return directoryStream
          .map(Path::toAbsolutePath)
          .map(Path::toString)
          .toList();
    }
  }
}