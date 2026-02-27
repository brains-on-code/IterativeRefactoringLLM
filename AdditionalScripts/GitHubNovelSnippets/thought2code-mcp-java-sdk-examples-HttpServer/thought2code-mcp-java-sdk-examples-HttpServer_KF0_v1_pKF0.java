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

  private static final Logger log = LoggerFactory.getLogger(HttpServer.class);

  private static final String DEFAULT_SERVLET_CONTEXT_PATH = "/";
  private static final String DEFAULT_SERVLET_PATH = "/*";
  private static final long DEFAULT_STOP_TIMEOUT_MILLIS = Duration.ofSeconds(5).toMillis();
  private static final String LOCALHOST_URL_TEMPLATE = "http://127.0.0.1:{}";

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
    if (servlet == null) {
      throw new IllegalStateException("Servlet must be configured before starting the server.");
    }

    Server server = createServer();
    configureShutdownHook(server);

    try {
      server.start();
      log.info("Jetty-based HTTP server started on {}", LOCALHOST_URL_TEMPLATE.formatted(port));
      server.join();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("HTTP server thread interrupted", e);
    } catch (Exception e) {
      log.error("Error starting HTTP server on {}", LOCALHOST_URL_TEMPLATE.formatted(port), e);
    }
  }

  private Server createServer() {
    ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    handler.setContextPath(DEFAULT_SERVLET_CONTEXT_PATH);

    ServletHolder servletHolder = new ServletHolder(servlet);
    handler.addServlet(servletHolder, DEFAULT_SERVLET_PATH);

    Server server = new Server(port);
    server.setHandler(handler);
    server.setStopAtShutdown(true);
    server.setStopTimeout(DEFAULT_STOP_TIMEOUT_MILLIS);

    return server;
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
}