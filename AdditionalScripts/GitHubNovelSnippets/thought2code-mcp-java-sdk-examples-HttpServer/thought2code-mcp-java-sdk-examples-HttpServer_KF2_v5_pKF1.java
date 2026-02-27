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
  private static final String SERVLET_MAPPING_ALL_PATHS = "/*";
  private static final long SERVER_STOP_TIMEOUT_SECONDS = 5L;

  private HttpServlet httpServlet;
  private int httpPort;

  public HttpServer withServlet(HttpServlet httpServlet) {
    this.httpServlet = httpServlet;
    return this;
  }

  public HttpServer withPort(int httpPort) {
    this.httpPort = httpPort;
    return this;
  }

  public void start() {
    ServletContextHandler servletContextHandler =
        new ServletContextHandler(ServletContextHandler.SESSIONS);
    servletContextHandler.setContextPath(ROOT_CONTEXT_PATH);

    ServletHolder servletHolder = new ServletHolder(httpServlet);
    servletContextHandler.addServlet(servletHolder, SERVLET_MAPPING_ALL_PATHS);

    Server jettyServer = new Server(httpPort);
    jettyServer.setHandler(servletContextHandler);
    jettyServer.setStopAtShutdown(true);
    jettyServer.setStopTimeout(Duration.ofSeconds(SERVER_STOP_TIMEOUT_SECONDS).toMillis());

    try {
      jettyServer.start();
      registerShutdownHook(jettyServer);
      LOGGER.info("Jetty-based HTTP server started on http://127.0.0.1:{}", httpPort);
    } catch (Exception exception) {
      LOGGER.error("Error starting HTTP server on http://127.0.0.1:{}", httpPort, exception);
    }

    try {
      jettyServer.join();
    } catch (InterruptedException interruptedException) {
      Thread.currentThread().interrupt();
      LOGGER.error("Error joining HTTP server", interruptedException);
    }
  }

  private void registerShutdownHook(Server jettyServer) {
    Runnable shutdownTask =
        () -> {
          try {
            LOGGER.info("Shutting down HTTP server and MCP server");
            jettyServer.stop();
          } catch (Exception exception) {
            LOGGER.error("Error stopping HTTP server and MCP server", exception);
          }
        };
    Thread shutdownHookThread = new Thread(shutdownTask, "http-server-shutdown-hook");
    Runtime.getRuntime().addShutdownHook(shutdownHookThread);
  }
}