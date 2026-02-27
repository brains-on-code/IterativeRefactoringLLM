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
    // Utility class; prevent instantiation.
  }

  /**
   * Reads a classpath resource into a String using UTF-8 encoding.
   *
   * @param filename the resource path
   * @return the resource contents as a String
   * @throws IOException if the resource cannot be read or does not exist
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
   * Reads a file from the filesystem into a String using the platform line separator.
   *
   * @param filepath the path to the file
   * @return the file contents as a String
   * @throws IOException if the file cannot be read
   */
  public static String readAsString(Path filepath) throws IOException {
    return String.join(System.lineSeparator(), Files.readAllLines(filepath));
  }

  /**
   * Performs a fuzzy search for files whose names match the given filename,
   * starting from the given directory. Uses platform-specific shell commands.
   *
   * @param start the starting directory
   * @param filename the filename pattern to search for
   * @return a list of matching file paths
   * @throws IOException if the search command fails
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
   * Lists the contents of a directory, returning absolute paths as strings.
   *
   * @param dir the directory to list
   * @return a list of absolute paths for each entry in the directory
   * @throws IOException if the directory cannot be read
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