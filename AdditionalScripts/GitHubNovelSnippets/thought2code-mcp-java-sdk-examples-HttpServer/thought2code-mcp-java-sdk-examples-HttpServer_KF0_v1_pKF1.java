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
  /** Logger instance for logging server events and errors. */
  private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

  /** Default context path for the servlet. */
  private static final String DEFAULT_SERVLET_CONTEXT_PATH = "/";

  /** Default path mapping for the servlet. */
  private static final String DEFAULT_SERVLET_PATH = "/*";

  /** The servlet to be hosted by this HTTP server. */
  private HttpServlet httpServlet;

  /** The port on which the HTTP server will listen. */
  private int serverPort;

  /**
   * Sets the servlet to be hosted by this HTTP server.
   *
   * @param servlet the servlet to host
   * @return this HttpServer instance for method chaining
   */
  public HttpServer use(HttpServlet servlet) {
    this.httpServlet = servlet;
    return this;
  }

  /**
   * Sets the port on which the HTTP server will listen.
   *
   * @param port the port number
   * @return this HttpServer instance for method chaining
   */
  public HttpServer bind(int port) {
    this.serverPort = port;
    return this;
  }

  /**
   * Starts the HTTP server with the configured servlet and port. This method blocks indefinitely
   * until the server is stopped. The server will automatically shut down when the JVM receives a
   * shutdown signal.
   */
  public void start() {
    ServletContextHandler servletContextHandler =
        new ServletContextHandler(ServletContextHandler.SESSIONS);
    servletContextHandler.setContextPath(DEFAULT_SERVLET_CONTEXT_PATH);

    ServletHolder servletHolder = new ServletHolder(httpServlet);
    servletContextHandler.addServlet(servletHolder, DEFAULT_SERVLET_PATH);

    Server jettyServer = new Server(serverPort);
    jettyServer.setHandler(servletContextHandler);
    jettyServer.setStopAtShutdown(true);
    jettyServer.setStopTimeout(Duration.ofSeconds(5).getSeconds());

    try {
      jettyServer.start();
      addShutdownHook(jettyServer);
      LOGGER.info("Jetty-based HTTP server started on http://127.0.0.1:{}", serverPort);
    } catch (Exception e) {
      LOGGER.error("Error starting HTTP server on http://127.0.0.1:{}", serverPort, e);
    }

    try {
      jettyServer.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Error joining HTTP server", e);
    }
  }

  /**
   * Adds a shutdown hook to gracefully stop the server when the JVM shuts down.
   *
   * @param jettyServer the server to stop
   */
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
    Thread shutdownHook = new Thread(shutdownTask);
    Runtime.getRuntime().addShutdownHook(shutdownHook);
  }
}