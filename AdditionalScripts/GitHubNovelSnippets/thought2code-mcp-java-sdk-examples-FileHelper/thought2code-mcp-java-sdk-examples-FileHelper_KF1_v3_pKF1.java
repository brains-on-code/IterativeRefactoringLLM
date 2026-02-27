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
 * Utility methods for working with the file system and classpath resources.
 */
public final class FileSystemUtils {

  private FileSystemUtils() {
    // Utility class; prevent instantiation.
  }

  /**
   * Reads a classpath resource into a single {@link String} using UTF-8 encoding.
   *
   * @param resourcePath the path to the resource on the classpath
   * @return the contents of the resource as a string
   * @throws IOException if the resource cannot be read
   */
  public static String readResourceAsString(String resourcePath) throws IOException {
    ClassLoader classLoader = FileSystemUtils.class.getClassLoader();
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

  /**
   * Reads a file into a single {@link String} using the platform line separator.
   *
   * @param filePath the path to the file
   * @return the contents of the file as a string
   * @throws IOException if the file cannot be read
   */
  public static String readFileAsString(Path filePath) throws IOException {
    return String.join(System.lineSeparator(), Files.readAllLines(filePath));
  }

  /**
   * Finds files by name pattern under the given directory path.
   *
   * @param directoryPath   the root directory to search
   * @param fileNamePattern the file name pattern to match
   * @return a list of matching file paths as strings
   * @throws IOException if the search command fails
   */
  public static List<String> findFilesByName(String directoryPath, String fileNamePattern)
      throws IOException {
    final boolean isWindows =
        System.getProperty("os.name").toLowerCase().contains("win");
    if (isWindows) {
      return ShellHelper.exec(
          "cmd.exe", "/c", "dir", directoryPath, "/s", "/b", "|", "findstr", fileNamePattern);
    }
    return ShellHelper.exec("sh", "-c", "find", directoryPath, "-name", fileNamePattern);
  }

  /**
   * Lists the contents of a directory, returning absolute paths as strings.
   *
   * @param directoryPath the directory to list
   * @return a list of absolute paths of the directory contents
   * @throws IOException if the directory cannot be listed
   */
  public static List<String> listDirectoryContents(String directoryPath) throws IOException {
    try (Stream<Path> directoryStream = Files.list(Path.of(directoryPath))) {
      return directoryStream
          .map(Path::toAbsolutePath)
          .map(Path::toString)
          .toList();
    }
  }
}