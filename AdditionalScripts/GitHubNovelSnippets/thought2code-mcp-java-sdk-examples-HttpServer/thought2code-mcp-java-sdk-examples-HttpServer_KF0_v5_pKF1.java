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
  private static final long STOP_TIMEOUT_SECONDS = 5L;

  private HttpServlet httpServlet;
  private int serverPort;

  public HttpServer withServlet(HttpServlet httpServlet) {
    this.httpServlet = httpServlet;
    return this;
  }

  public HttpServer withPort(int serverPort) {
    this.serverPort = serverPort;
    return this;
  }

  public void start() {
    ServletContextHandler servletContextHandler =
        new ServletContextHandler(ServletContextHandler.SESSIONS);
    servletContextHandler.setContextPath(ROOT_CONTEXT_PATH);

    ServletHolder servletHolder = new ServletHolder(httpServlet);
    servletContextHandler.addServlet(servletHolder, SERVLET_URL_PATTERN);

    Server jettyServer = new Server(serverPort);
    jettyServer.setHandler(servletContextHandler);
    jettyServer.setStopAtShutdown(true);
    jettyServer.setStopTimeout(Duration.ofSeconds(STOP_TIMEOUT_SECONDS).toMillis());

    try {
      jettyServer.start();
      addShutdownHook(jettyServer);
      LOGGER.info("Jetty-based HTTP server started on http://127.0.0.1:{}", serverPort);
    } catch (Exception startupException) {
      LOGGER.error(
          "Error starting HTTP server on http://127.0.0.1:{}", serverPort, startupException);
    }

    try {
      jettyServer.join();
    } catch (InterruptedException joinException) {
      Thread.currentThread().interrupt();
      LOGGER.error("Error joining HTTP server", joinException);
    }
  }

  private void addShutdownHook(Server jettyServer) {
    Runnable shutdownTask =
        () -> {
          try {
            LOGGER.info("Shutting down HTTP server and MCP server");
            jettyServer.stop();
          } catch (Exception shutdownException) {
            LOGGER.error("Error stopping HTTP server and MCP server", shutdownException);
          }
        };
    Thread shutdownHookThread = new Thread(shutdownTask, "http-server-shutdown-hook");
    Runtime.getRuntime().addShutdownHook(shutdownHookThread);
  }
}