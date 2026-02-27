package com.github.mcp.server.filesystem.common;

import jakarta.servlet.http.HttpServlet;
import java.time.Duration;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Jetty-based HTTP server that hosts a single servlet.
 */
public class HttpServer {

  private static final Logger log = LoggerFactory.getLogger(HttpServer.class);

  private static final String DEFAULT_SERVLET_CONTEXT_PATH = "/";
  private static final String DEFAULT_SERVLET_PATH = "/*";
  private static final Duration DEFAULT_STOP_TIMEOUT = Duration.ofSeconds(5);

  private HttpServlet servlet;
  private int port;

  public HttpServer use(HttpServlet servlet) {
    this.servlet = servlet;
    return this;
  }

  public HttpServer bind(int port) {
    this.port = port;
    return this;
  }

  public void start() {
    Server server = createServer();
    configureShutdown(server);

    try {
      server.start();
      log.info("HTTP server started on http://127.0.0.1:{}", port);
    } catch (Exception e) {
      log.error("Error starting HTTP server on http://127.0.0.1:{}", port, e);
      return;
    }

    waitForServer(server);
  }

  private Server createServer() {
    ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    handler.setContextPath(DEFAULT_SERVLET_CONTEXT_PATH);

    ServletHolder servletHolder = new ServletHolder(servlet);
    handler.addServlet(servletHolder, DEFAULT_SERVLET_PATH);

    Server server = new Server(port);
    server.setHandler(handler);
    server.setStopAtShutdown(true);
    server.setStopTimeout(DEFAULT_STOP_TIMEOUT.toMillis());

    return server;
  }

  private void configureShutdown(Server server) {
    Thread shutdownHook =
        new Thread(
            () -> {
              try {
                log.info("Shutting down HTTP server and MCP server");
                server.stop();
              } catch (Exception e) {
                log.error("Error stopping HTTP server and MCP server", e);
              }
            });

    Runtime.getRuntime().addShutdownHook(shutdownHook);
  }

  private void waitForServer(Server server) {
    try {
      server.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("HTTP server thread interrupted", e);
    }
  }
}