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

  private static final String ROOT_CONTEXT_PATH = "/";
  private static final String DEFAULT_SERVLET_MAPPING = "/*";
  private static final long STOP_TIMEOUT_MILLIS = Duration.ofSeconds(5).toMillis();

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
    contextHandler.setContextPath(ROOT_CONTEXT_PATH);

    ServletHolder servletHolder = new ServletHolder(servlet);
    contextHandler.addServlet(servletHolder, DEFAULT_SERVLET_MAPPING);

    Server server = new Server(port);
    server.setHandler(contextHandler);
    server.setStopAtShutdown(true);
    server.setStopTimeout(STOP_TIMEOUT_MILLIS);

    try {
      server.start();
      registerShutdownHook(server);
      LOGGER.info("Jetty-based HTTP server started on http://127.0.0.1:{}", port);
    } catch (Exception startupException) {
      LOGGER.error("Error starting HTTP server on http://127.0.0.1:{}", port, startupException);
    }

    try {
      server.join();
    } catch (InterruptedException joinException) {
      LOGGER.error("Error joining HTTP server", joinException);
      Thread.currentThread().interrupt();
    }
  }

  private void registerShutdownHook(Server server) {
    Runnable shutdownTask =
        () -> {
          try {
            LOGGER.info("Shutting down HTTP server and MCP server");
            server.stop();
          } catch (Exception shutdownException) {
            LOGGER.error("Error stopping HTTP server and MCP server", shutdownException);
          }
        };
    Thread shutdownHookThread = new Thread(shutdownTask);
    Runtime.getRuntime().addShutdownHook(shutdownHookThread);
  }
}