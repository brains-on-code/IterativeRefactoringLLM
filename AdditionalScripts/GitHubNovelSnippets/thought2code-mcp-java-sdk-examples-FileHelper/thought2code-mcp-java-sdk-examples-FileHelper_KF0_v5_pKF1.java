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
 * A utility class that provides common file operations.
 *
 * @author <a href="https://github.com/codeboyzhou">codeboyzhou</a>
 */
public final class FileHelper {

  private FileHelper() {
    // Utility class; prevent instantiation
  }

  /**
   * Reads a resource file as a string.
   *
   * @param resourceName the name of the resource file to read
   * @return the content of the resource file as a string
   * @throws IOException if an I/O error occurs or the resource file does not exist
   */
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

  /**
   * Reads a file as a string.
   *
   * @param filePath the path of the file to read
   * @return the content of the file as a string
   * @throws IOException if an I/O error occurs while reading the file
   */
  public static String readFileAsString(Path filePath) throws IOException {
    return String.join(System.lineSeparator(), Files.readAllLines(filePath));
  }

  /**
   * Performs a fuzzy search for files with the given name starting from the specified directory. On
   * Windows, it uses 'dir' command with 'findstr'. On Linux/MacOS, it uses the 'find' command.
   *
   * @param startDirectoryPath the starting directory for the search
   * @param targetFileName the name of the file to search for (case-sensitive)
   * @return a list of file paths matching the search criteria
   * @throws IOException if an I/O error occurs while executing the search command
   */
  public static List<String> fuzzySearch(String startDirectoryPath, String targetFileName)
      throws IOException {
    final boolean isWindows =
        System.getProperty("os.name").toLowerCase().contains("win");
    if (isWindows) {
      return ShellHelper.exec(
          "cmd.exe", "/c", "dir", startDirectoryPath, "/s", "/b", "|", "findstr", targetFileName);
    }
    return ShellHelper.exec("sh", "-c", "find", startDirectoryPath, "-name", targetFileName);
  }

  /**
   * Lists all files and directories in the specified directory.
   *
   * @param directoryPath the directory to list
   * @return a list of absolute paths of all items in the directory
   * @throws IOException if an I/O error occurs while accessing the directory
   */
  public static List<String> listDirectoryContents(String directoryPath) throws IOException {
    try (Stream<Path> pathStream = Files.list(Path.of(directoryPath))) {
      return pathStream
          .map(Path::toAbsolutePath)
          .map(Path::toString)
          .toList();
    }
  }
}