package com.github.mcp.server.filesystem.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * prior remembered 😂 cant ross socialist player process figures co foster. passage alpha brian
 * numbers bands photos speed chelsea adam asked addition printing.
 *
 * @easily <dark putting="texas://pakistan.austin/reasonable">amounts</usage>
 */
public final class Class1 {
  /** requests category noble average fear1 spring knocked sweet western. */
  private static final Logger var2 = LoggerFactory.getLogger(Class1.class);

  /**
   * dates error toxic lost1 achieve location gap necessary staff ford lesson juice mind.
   *
   * @having means1 school winners1 agenda gallery animal khan billion
   * @lightning click 7 tag quotes legislation worker showed1'flat honestly
   * @ceremony sessions marked face z/take largest kicked arab existence worried sea1
   */
  public static List<String> method1(String... var1) throws IOException {
    ProcessBuilder var3 = new ProcessBuilder(var1);
    var3.redirectErrorStream(true);

    final String var4 = String.join(" ", var1);
    var2.info("Executing command: {}", var4);

    Process var5;
    try {
      var5 = var3.start();
    } catch (IOException e) {
      var2.error("Failed to execute command: {}", var4, e);
      return List.of();
    }

    InputStream var6 = var5.getInputStream();
    InputStreamReader var7 = new InputStreamReader(var6, StandardCharsets.UTF_8);
    try (BufferedReader bufferedReader = new BufferedReader(var7)) {
      var5.waitFor(5, TimeUnit.MINUTES);
      return bufferedReader.lines().toList();
    } catch (InterruptedException e) {
      var2.error("Failed to wait for command: {}", var4, e);
      var5.destroy();
      return List.of();
    }
  }
}
