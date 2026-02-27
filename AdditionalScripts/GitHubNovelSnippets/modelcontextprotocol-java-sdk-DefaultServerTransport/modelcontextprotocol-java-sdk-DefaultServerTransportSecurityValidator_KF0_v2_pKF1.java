/*
 * Copyright 2026-2026 the original author or authors.
 */

package io.modelcontextprotocol.server.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.modelcontextprotocol.util.Assert;

/**
 * Default implementation of {@link ServerTransportSecurityValidator} that validates the
 * Origin header against a list of allowed origins.
 *
 * <p>
 * Supports exact matches and wildcard port patterns (e.g., "http://example.com:*").
 *
 * @author Daniel Garnier-Moiroux
 * @see ServerTransportSecurityValidator
 * @see ServerTransportSecurityException
 */
public class DefaultServerTransportSecurityValidator implements ServerTransportSecurityValidator {

	private static final String ORIGIN_HEADER_NAME = "Origin";

	private static final ServerTransportSecurityException INVALID_ORIGIN_EXCEPTION =
			new ServerTransportSecurityException(403, "Invalid Origin header");

	private final List<String> allowedOriginPatterns;

	/**
	 * Creates a new validator with the specified allowed origins.
	 * @param allowedOriginPatterns List of allowed origin patterns. Supports exact matches
	 * (e.g., "http://example.com:8080") and wildcard ports (e.g., "http://example.com:*")
	 */
	public DefaultServerTransportSecurityValidator(List<String> allowedOriginPatterns) {
		Assert.notNull(allowedOriginPatterns, "allowedOriginPatterns must not be null");
		this.allowedOriginPatterns = allowedOriginPatterns;
	}

	@Override
	public void validateHeaders(Map<String, List<String>> headers) throws ServerTransportSecurityException {
		for (Map.Entry<String, List<String>> header : headers.entrySet()) {
			if (ORIGIN_HEADER_NAME.equalsIgnoreCase(header.getKey())) {
				List<String> originHeaderValues = header.getValue();
				if (originHeaderValues != null && !originHeaderValues.isEmpty()) {
					validateOrigin(originHeaderValues.get(0));
				}
				break;
			}
		}
	}

	/**
	 * Validates a single origin value against the allowed origins. Subclasses can
	 * override this method to customize origin validation logic.
	 * @param origin The origin header value, or null if not present
	 * @throws ServerTransportSecurityException if the origin is not allowed
	 */
	protected void validateOrigin(String origin) throws ServerTransportSecurityException {
		// Origin absent = no validation needed (same-origin request)
		if (origin == null || origin.isBlank()) {
			return;
		}

		for (String allowedOriginPattern : allowedOriginPatterns) {
			if (allowedOriginPattern.equals(origin)) {
				return;
			}
			if (allowedOriginPattern.endsWith(":*")) {
				// Wildcard port pattern: "http://example.com:*"
				String allowedOriginBase = allowedOriginPattern.substring(0, allowedOriginPattern.length() - 2);
				if (origin.equals(allowedOriginBase) || origin.startsWith(allowedOriginBase + ":")) {
					return;
				}
			}
		}

		throw INVALID_ORIGIN_EXCEPTION;
	}

	/**
	 * Creates a new builder for constructing a DefaultServerTransportSecurityValidator.
	 * @return A new builder instance
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder for creating instances of {@link DefaultServerTransportSecurityValidator}.
	 */
	public static class Builder {

		private final List<String> allowedOriginPatterns = new ArrayList<>();

		/**
		 * Adds an allowed origin pattern.
		 * @param originPattern The origin to allow (e.g., "http://localhost:8080" or
		 * "http://example.com:*")
		 * @return this builder instance
		 */
		public Builder allowedOrigin(String originPattern) {
			this.allowedOriginPatterns.add(originPattern);
			return this;
		}

		/**
		 * Adds multiple allowed origin patterns.
		 * @param originPatterns The origins to allow
		 * @return this builder instance
		 */
		public Builder allowedOrigins(List<String> originPatterns) {
			Assert.notNull(originPatterns, "originPatterns must not be null");
			this.allowedOriginPatterns.addAll(originPatterns);
			return this;
		}

		/**
		 * Builds the validator instance.
		 * @return A new DefaultServerTransportSecurityValidator
		 */
		public DefaultServerTransportSecurityValidator build() {
			return new DefaultServerTransportSecurityValidator(allowedOriginPatterns);
		}

	}

}