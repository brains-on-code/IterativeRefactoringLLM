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

  public static String readResourceAsString(String filename) throws IOException {
    ClassLoader classLoader = FileHelper.class.getClassLoader();
    try (InputStream inputStream = classLoader.getResourceAsStream(filename)) {
      if (inputStream == null) {
        throw new NoSuchFileException(filename);
      }
      InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
      try (BufferedReader bufferedReader = new BufferedReader(streamReader)) {
        return bufferedReader.lines().collect(joining(System.lineSeparator()));
      }
    }
  }


  public static String readAsString(Path filepath) throws IOException {
    return String.join(System.lineSeparator(), Files.readAllLines(filepath));
  }


  public static List<String> fuzzySearch(String start, String filename) throws IOException {
    final boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
    if (isWindows) {
      return ShellHelper.exec("cmd.exe", "/c", "dir", start, "/s", "/b", "|", "findstr", filename);
    }
    return ShellHelper.exec("sh", "-c", "find", start, "-name", filename);
  }


  public static List<String> listDirectory(String dir) throws IOException {
    try (Stream<Path> stream = Files.list(Path.of(dir))) {
      return stream.map(Path::toAbsolutePath).map(Path::toString).toList();
    }
  }
}
