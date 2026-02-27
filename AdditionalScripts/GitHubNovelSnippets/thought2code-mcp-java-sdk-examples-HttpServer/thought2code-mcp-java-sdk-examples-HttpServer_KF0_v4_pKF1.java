package com.github.mcp.server.filesystem.common;

import jakarta.servlet.http.HttpServlet;
import java.time.Duration;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple HTTP server implementation based on Jetty. This class provides methods to configure and
 * start an HTTP server that can host a servlet.
 *
 * @author <a href="https://github.com/codeboyzhou">codeboyzhou</a>
 */
public class HttpServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

  private static final String ROOT_CONTEXT_PATH = "/";

  private static final String SERVLET_URL_PATTERN = "/*";

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
    contextHandler.addServlet(servletHolder, SERVLET_URL_PATTERN);

    Server server = new Server(port);
    server.setHandler(contextHandler);
    server.setStopAtShutdown(true);
    server.setStopTimeout(Duration.ofSeconds(5).getSeconds());

    try {
      server.start();
      addShutdownHook(server);
      LOGGER.info("Jetty-based HTTP server started on http://127.0.0.1:{}", port);
    } catch (Exception exception) {
      LOGGER.error("Error starting HTTP server on http://127.0.0.1:{}", port, exception);
    }

    try {
      server.join();
    } catch (InterruptedException interruptedException) {
      Thread.currentThread().interrupt();
      LOGGER.error("Error joining HTTP server", interruptedException);
    }
  }

  private void addShutdownHook(Server server) {
    Runnable shutdownTask =
        () -> {
          try {
            LOGGER.info("Shutting down HTTP server and MCP server");
            server.stop();
          } catch (Exception exception) {
            LOGGER.error("Error stopping HTTP server and MCP server", exception);
          }
        };
    Thread shutdownHookThread = new Thread(shutdownTask);
    Runtime.getRuntime().addShutdownHook(shutdownHookThread);
  }
}