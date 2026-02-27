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

  private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
  private static final String LINE_SEPARATOR = System.lineSeparator();

  private FileHelper() {
    // Utility class; prevent instantiation
  }

  /**
   * Reads a resource file as a string.
   *
   * @param filename the name of the resource file to read
   * @return the content of the resource file as a string
   * @throws IOException if an I/O error occurs or the resource file does not exist
   */
  public static String readResourceAsString(String filename) throws IOException {
    ClassLoader classLoader = FileHelper.class.getClassLoader();

    try (InputStream inputStream = classLoader.getResourceAsStream(filename)) {
      if (inputStream == null) {
        throw new NoSuchFileException(filename);
      }

      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        return reader.lines().collect(joining(LINE_SEPARATOR));
      }
    }
  }

  /**
   * Reads a file as a string.
   *
   * @param filepath the path of the file to read
   * @return the content of the file as a string
   * @throws IOException if an I/O error occurs while reading the file
   */
  public static String readAsString(Path filepath) throws IOException {
    return Files.readString(filepath, StandardCharsets.UTF_8);
  }

  /**
   * Performs a fuzzy search for files with the given name starting from the specified directory. On
   * Windows, it uses 'dir' command with 'findstr'. On Linux/MacOS, it uses the 'find' command.
   *
   * @param start the starting directory for the search
   * @param filename the name of the file to search for (case-sensitive)
   * @return a list of file paths matching the search criteria
   * @throws IOException if an I/O error occurs while executing the search command
   */
  public static List<String> fuzzySearch(String start, String filename) throws IOException {
    if (isWindows()) {
      return ShellHelper.exec(
          "cmd.exe", "/c", "dir", start, "/s", "/b", "|", "findstr", filename);
    }

    return ShellHelper.exec("sh", "-c", "find", start, "-name", filename);
  }

  /**
   * Lists all files and directories in the specified directory.
   *
   * @param dir the directory to list
   * @return a list of absolute paths of all items in the directory
   * @throws IOException if an I/O error occurs while accessing the directory
   */
  public static List<String> listDirectory(String dir) throws IOException {
    Path directory = Path.of(dir);
    try (Stream<Path> stream = Files.list(directory)) {
      return stream
          .map(Path::toAbsolutePath)
          .map(Path::toString)
          .toList();
    }
  }

  private static boolean isWindows() {
    return OS_NAME.contains("win");
  }
}