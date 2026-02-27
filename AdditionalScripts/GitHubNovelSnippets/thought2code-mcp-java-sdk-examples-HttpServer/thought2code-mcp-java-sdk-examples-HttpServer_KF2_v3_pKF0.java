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

  private static final Logger log = LoggerFactory.getLogger(HttpServer.class);

  private static final String DEFAULT_SERVLET_CONTEXT_PATH = "/";
  private static final String DEFAULT_SERVLET_PATH = "/*";
  private static final long STOP_TIMEOUT_SECONDS = 5L;
  private static final String LOCALHOST_URL_TEMPLATE = "http://127.0.0.1:%d";

  private HttpServlet servlet;
  private int port;

  public HttpServer use(HttpServlet servlet) {
    this.servlet = Objects.requireNonNull(servlet, "servlet must not be null");
    return this;
  }

  public HttpServer bind(int port) {
    this.port = port;
    return this;
  }

  public void start() {
    validateConfiguration();

    Server httpServer = createServer();
    addShutdownHook(httpServer);

    try {
      httpServer.start();
      log.info("Jetty-based HTTP server started on {}", getLocalhostUrl());
      httpServer.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("HTTP server thread was interrupted", e);
    } catch (Exception e) {
      log.error("Error starting or running HTTP server on {}", getLocalhostUrl(), e);
    }
  }

  private void validateConfiguration() {
    if (servlet == null) {
      throw new IllegalStateException("Servlet must be set before starting the server.");
    }
    if (port <= 0) {
      throw new IllegalStateException("Port must be a positive integer.");
    }
  }

  private Server createServer() {
    Server httpServer = new Server(port);
    httpServer.setHandler(createServletContextHandler());
    httpServer.setStopAtShutdown(true);
    httpServer.setStopTimeout(Duration.ofSeconds(STOP_TIMEOUT_SECONDS).toMillis());
    return httpServer;
  }

  private ServletContextHandler createServletContextHandler() {
    ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    handler.setContextPath(DEFAULT_SERVLET_CONTEXT_PATH);

    ServletHolder servletHolder = new ServletHolder(servlet);
    handler.addServlet(servletHolder, DEFAULT_SERVLET_PATH);

    return handler;
  }

  private void addShutdownHook(Server httpServer) {
    Runtime.getRuntime()
        .addShutdownHook(new Thread(() -> stopServer(httpServer), "http-server-shutdown-hook"));
  }

  private void stopServer(Server httpServer) {
    try {
      log.info("Shutting down HTTP server and MCP server");
      httpServer.stop();
    } catch (Exception e) {
      log.error("Error stopping HTTP server and MCP server", e);
    }
  }

  private String getLocalhostUrl() {
    return String.format(LOCALHOST_URL_TEMPLATE, port);
  }
}