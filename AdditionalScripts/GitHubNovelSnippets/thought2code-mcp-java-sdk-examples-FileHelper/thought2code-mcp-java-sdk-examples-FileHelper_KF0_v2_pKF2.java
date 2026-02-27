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
 * Utility methods for common file operations.
 */
public final class FileHelper {

  private FileHelper() {
    // Prevent instantiation.
  }

  /**
   * Reads a classpath resource into a string using UTF-8.
   *
   * @param filename resource path relative to the classpath root
   * @return resource contents
   * @throws IOException if the resource cannot be found or read
   */
  public static String readResourceAsString(String filename) throws IOException {
    ClassLoader classLoader = FileHelper.class.getClassLoader();

    try (InputStream inputStream = classLoader.getResourceAsStream(filename)) {
      if (inputStream == null) {
        throw new NoSuchFileException(filename);
      }

      try (BufferedReader reader =
               new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        return reader.lines().collect(joining(System.lineSeparator()));
      }
    }
  }

  /**
   * Reads a file into a string using the platform line separator.
   *
   * @param filepath path to the file
   * @return file contents
   * @throws IOException if the file cannot be read
   */
  public static String readAsString(Path filepath) throws IOException {
    return String.join(System.lineSeparator(), Files.readAllLines(filepath));
  }

  /**
   * Performs a fuzzy search for files with the given name starting from the specified directory.
   *
   * <p>On Windows, this uses {@code dir} piped to {@code findstr}. On Unix-like systems, this uses
   * {@code find}.
   *
   * @param start starting directory
   * @param filename file name to search for (case-sensitive)
   * @return list of matching file paths
   * @throws IOException if the underlying shell command fails
   */
  public static List<String> fuzzySearch(String start, String filename) throws IOException {
    boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

    if (isWindows) {
      return ShellHelper.exec(
          "cmd.exe", "/c", "dir", start, "/s", "/b", "|", "findstr", filename);
    }

    return ShellHelper.exec("sh", "-c", "find", start, "-name", filename);
  }

  /**
   * Lists all entries in the given directory.
   *
   * @param dir directory path
   * @return list of absolute paths of directory entries
   * @throws IOException if the directory cannot be accessed
   */
  public static List<String> listDirectory(String dir) throws IOException {
    try (Stream<Path> stream = Files.list(Path.of(dir))) {
      return stream
          .map(Path::toAbsolutePath)
          .map(Path::toString)
          .toList();
    }
  }
}