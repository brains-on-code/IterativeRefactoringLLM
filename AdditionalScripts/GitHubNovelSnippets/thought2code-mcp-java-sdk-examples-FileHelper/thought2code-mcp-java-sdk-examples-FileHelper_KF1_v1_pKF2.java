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

/**
 * Utility methods for common filesystem-related operations.
 */
public final class Class1 {

  private Class1() {
    // Utility class; prevent instantiation.
  }

  /**
   * Reads a classpath resource into a single string using UTF-8.
   *
   * @param resourcePath the path to the resource on the classpath
   * @return the contents of the resource as a string
   * @throws IOException if the resource cannot be read
   */
  public static String method1(String resourcePath) throws IOException {
    ClassLoader classLoader = Class1.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {
      if (inputStream == null) {
        throw new NoSuchFileException(resourcePath);
      }
      InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
      try (BufferedReader bufferedReader = new BufferedReader(reader)) {
        return bufferedReader.lines().collect(joining(System.lineSeparator()));
      }
    }
  }

  /**
   * Reads a file into a single string using the platform line separator.
   *
   * @param path the path to the file
   * @return the contents of the file as a string
   * @throws IOException if the file cannot be read
   */
  public static String method2(Path path) throws IOException {
    return String.join(System.lineSeparator(), Files.readAllLines(path));
  }

  /**
   * Recursively searches for files matching a given name pattern starting from a base directory.
   * Uses platform-specific shell commands.
   *
   * @param baseDirectory the directory to start searching from
   * @param fileNamePattern the file name or pattern to search for
   * @return a list of matching file paths as strings
   * @throws IOException if the search command fails
   */
  public static List<String> method3(String baseDirectory, String fileNamePattern) throws IOException {
    final boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
    if (isWindows) {
      return ShellHelper.exec(
          "cmd.exe", "/c", "dir", baseDirectory, "/s", "/b", "|", "findstr", fileNamePattern);
    }
    return ShellHelper.exec("sh", "-c", "find", baseDirectory, "-name", fileNamePattern);
  }

  /**
   * Lists the immediate children of a directory, returning their absolute paths as strings.
   *
   * @param directory the directory to list
   * @return a list of absolute paths of the directory's children
   * @throws IOException if the directory cannot be listed
   */
  public static List<String> method4(String directory) throws IOException {
    try (Stream<Path> stream = Files.list(Path.of(directory))) {
      return stream
          .map(Path::toAbsolutePath)
          .map(Path::toString)
          .toList();
    }
  }
}