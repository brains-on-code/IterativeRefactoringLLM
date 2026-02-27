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

  public static String readResourceAsString(String resourceName) throws IOException {
    ClassLoader classLoader = FileHelper.class.getClassLoader();
    try (InputStream resourceStream = classLoader.getResourceAsStream(resourceName)) {
      if (resourceStream == null) {
        throw new NoSuchFileException(resourceName);
      }
      InputStreamReader inputStreamReader =
          new InputStreamReader(resourceStream, StandardCharsets.UTF_8);
      try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
        return bufferedReader.lines().collect(joining(System.lineSeparator()));
      }
    }
  }

  public static String readFileAsString(Path filePath) throws IOException {
    return String.join(System.lineSeparator(), Files.readAllLines(filePath));
  }

  public static List<String> fuzzySearch(String searchRoot, String fileNamePattern)
      throws IOException {
    final boolean isWindowsOs =
        System.getProperty("os.name").toLowerCase().contains("win");
    if (isWindowsOs) {
      return ShellHelper.exec(
          "cmd.exe", "/c", "dir", searchRoot, "/s", "/b", "|", "findstr", fileNamePattern);
    }
    return ShellHelper.exec("sh", "-c", "find", searchRoot, "-name", fileNamePattern);
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