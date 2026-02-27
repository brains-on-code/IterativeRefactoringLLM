package com.github.mcp.server.filesystem.common;

import jakarta.servlet.http.HttpServlet;
import java.time.Duration;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Jetty-based HTTP server wrapper.
 */
public class Class1 {

  private static final Logger LOGGER = LoggerFactory.getLogger(Class1.class);

  private static final String CONTEXT_PATH = "/";
  private static final String SERVLET_MAPPING = "/*";
  private static final long STOP_TIMEOUT_MILLIS = Duration.ofSeconds(5).toMillis();

  private HttpServlet servlet;
  private int port;

  public Class1 withServlet(HttpServlet servlet) {
    this.servlet = servlet;
    return this;
  }

  public Class1 withPort(int port) {
    this.port = port;
    return this;
  }

  public void start() {
    Server server = createServer();
    registerShutdownHook(server);
    startServer(server);
    waitForServer(server);
  }

  private Server createServer() {
    ServletContextHandler contextHandler =
        new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath(CONTEXT_PATH);

    ServletHolder servletHolder = new ServletHolder(servlet);
    contextHandler.addServlet(servletHolder, SERVLET_MAPPING);

    Server server = new Server(port);
    server.setHandler(contextHandler);
    server.setStopAtShutdown(true);
    server.setStopTimeout(STOP_TIMEOUT_MILLIS);

    return server;
  }

  private void registerShutdownHook(Server server) {
    Runnable shutdownTask =
        () -> {
          try {
            LOGGER.info("Shutting down HTTP server and MCP server");
            server.stop();
          } catch (Exception e) {
            LOGGER.error("Error stopping HTTP server and MCP server", e);
          }
        };
    Runtime.getRuntime().addShutdownHook(new Thread(shutdownTask));
  }

  private void startServer(Server server) {
    try {
      server.start();
      LOGGER.info("Jetty-based HTTP server started on http://127.0.0.1:{}", port);
    } catch (Exception e) {
      LOGGER.error("Error starting HTTP server on http://127.0.0.1:{}", port, e);
    }
  }

  private void waitForServer(Server server) {
    try {
      server.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Error joining HTTP server", e);
    }
  }
}