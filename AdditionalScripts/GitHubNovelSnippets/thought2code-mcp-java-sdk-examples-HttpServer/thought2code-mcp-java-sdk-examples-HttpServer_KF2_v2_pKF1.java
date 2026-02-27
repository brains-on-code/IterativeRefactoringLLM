package com.github.mcp.server.filesystem.common;

import jakarta.servlet.http.HttpServlet;
import java.time.Duration;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

  private static final String CONTEXT_PATH_ROOT = "/";
  private static final String SERVLET_MAPPING_ALL = "/*";
  private static final long STOP_TIMEOUT_SECONDS = 5L;

  private HttpServlet servlet;
  private int port;

  public HttpServer withServlet(HttpServlet servlet) {
    this.servlet = servlet;
    return this;
  }

  public HttpServer withPort(int port) {
    this.port = port;
    return this;
  }

  public void start() {
    ServletContextHandler contextHandler =
        new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath(CONTEXT_PATH_ROOT);

    ServletHolder servletHolder = new ServletHolder(servlet);
    contextHandler.addServlet(servletHolder, SERVLET_MAPPING_ALL);

    Server server = new Server(port);
    server.setHandler(contextHandler);
    server.setStopAtShutdown(true);
    server.setStopTimeout(Duration.ofSeconds(STOP_TIMEOUT_SECONDS).toMillis());

    try {
      server.start();
      addShutdownHook(server);
      LOGGER.info("Jetty-based HTTP server started on http://127.0.0.1:{}", port);
    } catch (Exception e) {
      LOGGER.error("Error starting HTTP server on http://127.0.0.1:{}", port, e);
    }

    try {
      server.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Error joining HTTP server", e);
    }
  }

  private void addShutdownHook(Server server) {
    Runnable shutdownTask =
        () -> {
          try {
            LOGGER.info("Shutting down HTTP server and MCP server");
            server.stop();
          } catch (Exception e) {
            LOGGER.error("Error stopping HTTP server and MCP server", e);
          }
        };
    Thread shutdownHook = new Thread(shutdownTask, "http-server-shutdown-hook");
    Runtime.getRuntime().addShutdownHook(shutdownHook);
  }
}