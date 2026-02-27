package com.github.mcp.server.filesystem.common;

import jakarta.servlet.http.HttpServlet;
import java.time.Duration;
import java.util.Objects;
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

  private static final Logger log = LoggerFactory.getLogger(HttpServer.class);

  private static final String DEFAULT_SERVLET_CONTEXT_PATH = "/";
  private static final String DEFAULT_SERVLET_PATH = "/*";
  private static final long DEFAULT_STOP_TIMEOUT_MILLIS = Duration.ofSeconds(5).toMillis();
  private static final String LOCALHOST_URL_TEMPLATE = "http://127.0.0.1:%d";

  private HttpServlet servlet;
  private int port;

  public HttpServer use(HttpServlet servlet) {
    this.servlet = Objects.requireNonNull(servlet, "servlet must not be null");
    return this;
  }

  public HttpServer bind(int port) {
    if (port <= 0 || port > 65535) {
      throw new IllegalArgumentException("Port must be between 1 and 65535");
    }
    this.port = port;
    return this;
  }

  public void start() {
    ensureConfigured();

    Server server = createServer();
    configureShutdownHook(server);

    try {
      server.start();
      log.info("Jetty-based HTTP server started on {}", formattedLocalhostUrl());
      server.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("HTTP server thread interrupted", e);
    } catch (Exception e) {
      log.error("Error starting HTTP server on {}", formattedLocalhostUrl(), e);
    }
  }

  private void ensureConfigured() {
    if (servlet == null) {
      throw new IllegalStateException("Servlet must be configured before starting the server.");
    }
    if (port == 0) {
      throw new IllegalStateException("Port must be configured before starting the server.");
    }
  }

  private Server createServer() {
    ServletContextHandler handler = createServletContextHandler();
    Server server = new Server(port);
    server.setHandler(handler);
    server.setStopAtShutdown(true);
    server.setStopTimeout(DEFAULT_STOP_TIMEOUT_MILLIS);
    return server;
  }

  private ServletContextHandler createServletContextHandler() {
    ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    handler.setContextPath(DEFAULT_SERVLET_CONTEXT_PATH);

    ServletHolder servletHolder = new ServletHolder(servlet);
    handler.addServlet(servletHolder, DEFAULT_SERVLET_PATH);

    return handler;
  }

  private void configureShutdownHook(Server server) {
    Thread shutdownHook =
        new Thread(
            () -> {
              try {
                log.info("Shutting down HTTP server and MCP server");
                server.stop();
              } catch (Exception e) {
                log.error("Error stopping HTTP server and MCP server", e);
              }
            },
            "http-server-shutdown-hook");

    Runtime.getRuntime().addShutdownHook(shutdownHook);
  }

  private String formattedLocalhostUrl() {
    return LOCALHOST_URL_TEMPLATE.formatted(port);
  }
}