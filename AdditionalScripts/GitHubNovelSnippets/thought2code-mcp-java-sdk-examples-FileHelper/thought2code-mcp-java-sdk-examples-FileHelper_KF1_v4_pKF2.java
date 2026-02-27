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
public final class FileSystemUtils {

  private FileSystemUtils() {
    // Prevent instantiation.
  }

  /**
   * Reads a classpath resource into a single string using UTF-8.
   *
   * @param resourcePath path to the resource on the classpath
   * @return contents of the resource as a string
   * @throws IOException if the resource cannot be read
   */
  public static String readClasspathResource(String resourcePath) throws IOException {
    ClassLoader classLoader = FileSystemUtils.class.getClassLoader();

    try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {
      if (inputStream == null) {
        throw new NoSuchFileException(resourcePath);
      }

      try (BufferedReader reader =
               new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        return reader.lines().collect(joining(System.lineSeparator()));
      }
    }
  }

  /**
   * Reads a file into a single string using the platform line separator.
   *
   * @param path path to the file
   * @return contents of the file as a string
   * @throws IOException if the file cannot be read
   */
  public static String readFile(Path path) throws IOException {
    return String.join(System.lineSeparator(), Files.readAllLines(path));
  }

  /**
   * Recursively searches for files matching a given name pattern starting from a base directory.
   * Uses platform-specific shell commands.
   *
   * @param baseDirectory directory to start searching from
   * @param fileNamePattern file name or pattern to search for
   * @return list of matching file paths as strings
   * @throws IOException if the search command fails
   */
  public static List<String> findFiles(String baseDirectory, String fileNamePattern)
      throws IOException {

    boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

    if (isWindows) {
      return ShellHelper.exec(
          "cmd.exe", "/c", "dir", baseDirectory, "/s", "/b", "|", "findstr", fileNamePattern);
    }

    return ShellHelper.exec("sh", "-c", "find", baseDirectory, "-name", fileNamePattern);
  }

  /**
   * Lists the immediate children of a directory, returning their absolute paths as strings.
   *
   * @param directory directory to list
   * @return list of absolute paths of the directory's children
   * @throws IOException if the directory cannot be listed
   */
  public static List<String> listDirectoryChildren(String directory) throws IOException {
    try (Stream<Path> stream = Files.list(Path.of(directory))) {
      return stream
          .map(Path::toAbsolutePath)
          .map(Path::toString)
          .toList();
    }
  }
}