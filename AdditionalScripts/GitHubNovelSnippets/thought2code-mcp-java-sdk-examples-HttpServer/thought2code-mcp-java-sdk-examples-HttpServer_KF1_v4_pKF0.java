package com.github.mcp.server.filesystem.common;

import jakarta.servlet.http.HttpServlet;
import java.time.Duration;
import java.util.Objects;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

  private static final String CONTEXT_PATH = "/";
  private static final String SERVLET_MAPPING = "/*";
  private static final long STOP_TIMEOUT_MILLIS = Duration.ofSeconds(5).toMillis();
  private static final String LOCALHOST_URL_TEMPLATE = "http://127.0.0.1:%d";

  private HttpServlet servlet;
  private Integer port;

  public HttpServer withServlet(HttpServlet servlet) {
    this.servlet = Objects.requireNonNull(servlet, "servlet must not be null");
    return this;
  }

  public HttpServer withPort(int port) {
    if (port <= 0) {
      throw new IllegalArgumentException("Port must be a positive integer");
    }
    this.port = port;
    return this;
  }

  public void start() {
    validateConfiguration();

    Server server = createServer();
    configureShutdownHook(server);

    try {
      server.start();
      LOGGER.info("Jetty-based HTTP server started on {}", formatLocalhostUrl(port));
      server.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("HTTP server thread interrupted", e);
    } catch (Exception e) {
      LOGGER.error("Error starting HTTP server on {}", formatLocalhostUrl(port), e);
    }
  }

  private void validateConfiguration() {
    if (servlet == null) {
      throw new IllegalStateException("Servlet must be configured before starting the server");
    }
    if (port == null || port <= 0) {
      throw new IllegalStateException("Port must be a positive integer");
    }
  }

  private Server createServer() {
    Server server = new Server(port);
    server.setHandler(createContextHandler());
    server.setStopAtShutdown(true);
    server.setStopTimeout(STOP_TIMEOUT_MILLIS);
    return server;
  }

  private ServletContextHandler createContextHandler() {
    ServletContextHandler contextHandler =
        new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath(CONTEXT_PATH);

    ServletHolder servletHolder = new ServletHolder(servlet);
    contextHandler.addServlet(servletHolder, SERVLET_MAPPING);

    return contextHandler;
  }

  private void configureShutdownHook(Server server) {
    Thread shutdownThread =
        new Thread(
            () -> {
              try {
                LOGGER.info("Shutting down HTTP server and MCP server");
                server.stop();
              } catch (Exception e) {
                LOGGER.error("Error stopping HTTP server and MCP server", e);
              }
            },
            "http-server-shutdown-hook");

    Runtime.getRuntime().addShutdownHook(shutdownThread);
  }

  private String formatLocalhostUrl(int port) {
    return String.format(LOCALHOST_URL_TEMPLATE, port);
  }
}