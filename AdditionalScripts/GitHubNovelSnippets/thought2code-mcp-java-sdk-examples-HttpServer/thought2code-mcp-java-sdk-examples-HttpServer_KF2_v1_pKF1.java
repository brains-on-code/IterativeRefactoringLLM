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

  private static final String DEFAULT_CONTEXT_PATH = "/";
  private static final String DEFAULT_SERVLET_MAPPING = "/*";
  private static final long DEFAULT_STOP_TIMEOUT_SECONDS = 5L;

  private HttpServlet httpServlet;
  private int port;

  public HttpServer withServlet(HttpServlet httpServlet) {
    this.httpServlet = httpServlet;
    return this;
  }

  public HttpServer withPort(int port) {
    this.port = port;
    return this;
  }

  public void start() {
    ServletContextHandler contextHandler =
        new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.setContextPath(DEFAULT_CONTEXT_PATH);

    ServletHolder servletHolder = new ServletHolder(httpServlet);
    contextHandler.addServlet(servletHolder, DEFAULT_SERVLET_MAPPING);

    Server jettyServer = new Server(port);
    jettyServer.setHandler(contextHandler);
    jettyServer.setStopAtShutdown(true);
    jettyServer.setStopTimeout(Duration.ofSeconds(DEFAULT_STOP_TIMEOUT_SECONDS).toMillis());

    try {
      jettyServer.start();
      addShutdownHook(jettyServer);
      LOGGER.info("Jetty-based HTTP server started on http://127.0.0.1:{}", port);
    } catch (Exception e) {
      LOGGER.error("Error starting HTTP server on http://127.0.0.1:{}", port, e);
    }

    try {
      jettyServer.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Error joining HTTP server", e);
    }
  }

  private void addShutdownHook(Server jettyServer) {
    Runnable shutdownTask =
        () -> {
          try {
            LOGGER.info("Shutting down HTTP server and MCP server");
            jettyServer.stop();
          } catch (Exception e) {
            LOGGER.error("Error stopping HTTP server and MCP server", e);
          }
        };
    Thread shutdownHook = new Thread(shutdownTask, "http-server-shutdown-hook");
    Runtime.getRuntime().addShutdownHook(shutdownHook);
  }
}