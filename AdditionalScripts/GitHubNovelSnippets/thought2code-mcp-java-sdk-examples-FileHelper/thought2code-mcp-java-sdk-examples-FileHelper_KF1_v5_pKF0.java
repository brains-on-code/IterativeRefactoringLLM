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
 * Utility methods for reading resources and interacting with the filesystem.
 */
public final class FileSystemUtils {

  private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
  private static final boolean IS_WINDOWS = OS_NAME.contains("win");
  private static final String LINE_SEPARATOR = System.lineSeparator();

  private FileSystemUtils() {
    // Utility class; prevent instantiation
  }

  /**
   * Reads the contents of a classpath resource into a single string.
   *
   * @param resourcePath the path to the resource on the classpath
   * @return the contents of the resource as a string
   * @throws IOException if the resource cannot be read or does not exist
   */
  public static String readResourceAsString(String resourcePath) throws IOException {
    ClassLoader classLoader = FileSystemUtils.class.getClassLoader();

    try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {
      if (inputStream == null) {
        throw new NoSuchFileException(resourcePath);
      }

      try (BufferedReader reader =
               new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        return reader.lines().collect(joining(LINE_SEPARATOR));
      }
    }
  }

  /**
   * Reads the contents of a file into a single string.
   *
   * @param path the path to the file
   * @return the contents of the file as a string
   * @throws IOException if the file cannot be read
   */
  public static String readFileAsString(Path path) throws IOException {
    return Files.readString(path);
  }

  /**
   * Finds files in a directory tree whose names match the given pattern.
   *
   * @param directory       the root directory to search
   * @param fileNamePattern the file name pattern to match
   * @return a list of matching file paths as strings
   * @throws IOException if the search command fails
   */
  public static List<String> findFiles(String directory, String fileNamePattern) throws IOException {
    return IS_WINDOWS
        ? findFilesOnWindows(directory, fileNamePattern)
        : findFilesOnUnix(directory, fileNamePattern);
  }

  private static List<String> findFilesOnWindows(String directory, String fileNamePattern)
      throws IOException {
    return ShellHelper.exec(
        "cmd.exe", "/c", "dir", directory, "/s", "/b", "|", "findstr", fileNamePattern);
  }

  private static List<String> findFilesOnUnix(String directory, String fileNamePattern)
      throws IOException {
    return ShellHelper.exec("sh", "-c", "find", directory, "-name", fileNamePattern);
  }

  /**
   * Lists the immediate children of a directory as absolute path strings.
   *
   * @param directory the directory to list
   * @return a list of absolute path strings for the directory's contents
   * @throws IOException if the directory cannot be read
   */
  public static List<String> listDirectory(String directory) throws IOException {
    Path dirPath = Path.of(directory);
    try (Stream<Path> stream = Files.list(dirPath)) {
      return stream
          .map(Path::toAbsolutePath)
          .map(Path::toString)
          .toList();
    }
  }
}