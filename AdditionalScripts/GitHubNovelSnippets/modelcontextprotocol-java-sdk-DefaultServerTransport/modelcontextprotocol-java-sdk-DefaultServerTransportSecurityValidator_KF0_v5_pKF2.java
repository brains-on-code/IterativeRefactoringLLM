/*
 * Copyright 2026-2026 the original author or authors.
 */

package io.modelcontextprotocol.server.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.modelcontextprotocol.util.Assert;

/**
 * {@link ServerTransportSecurityValidator} implementation that validates the
 * {@code Origin} header against a configured allow-list.
 *
 * <p>Supports:</p>
 * <ul>
 *   <li>Exact origin matches (e.g. {@code http://example.com:8080})</li>
 *   <li>Wildcard port patterns (e.g. {@code http://example.com:*})</li>
 * </ul>
 */
public class DefaultServerTransportSecurityValidator implements ServerTransportSecurityValidator {

	private static final String ORIGIN_HEADER = "Origin";

	private static final ServerTransportSecurityException INVALID_ORIGIN =
			new ServerTransportSecurityException(403, "Invalid Origin header");

	private final List<String> allowedOrigins;

	/**
	 * Create a new validator with the given allowed origins.
	 *
	 * @param allowedOrigins list of allowed origin patterns; supports exact matches
	 * (e.g. {@code http://example.com:8080}) and wildcard ports
	 * (e.g. {@code http://example.com:*})
	 */
	public DefaultServerTransportSecurityValidator(List<String> allowedOrigins) {
		Assert.notNull(allowedOrigins, "allowedOrigins must not be null");
		this.allowedOrigins = allowedOrigins;
	}

	@Override
	public void validateHeaders(Map<String, List<String>> headers) throws ServerTransportSecurityException {
		List<String> originValues = headers.entrySet().stream()
				.filter(entry -> ORIGIN_HEADER.equalsIgnoreCase(entry.getKey()))
				.map(Map.Entry::getValue)
				.findFirst()
				.orElse(null);

		if (originValues == null || originValues.isEmpty()) {
			return;
		}

		validateOrigin(originValues.get(0));
	}

	/**
	 * Validate a single origin value against the configured allow-list.
	 *
	 * @param origin the {@code Origin} header value, or {@code null} if not present
	 * @throws ServerTransportSecurityException if the origin is not allowed
	 */
	protected void validateOrigin(String origin) throws ServerTransportSecurityException {
		if (origin == null || origin.isBlank()) {
			return;
		}

		for (String allowedOrigin : allowedOrigins) {
			if (isExactMatch(allowedOrigin, origin) || isWildcardPortMatch(allowedOrigin, origin)) {
				return;
			}
		}

		throw INVALID_ORIGIN;
	}

	private boolean isExactMatch(String allowedOrigin, String origin) {
		return allowedOrigin.equals(origin);
	}

	private boolean isWildcardPortMatch(String allowedOrigin, String origin) {
		if (!allowedOrigin.endsWith(":*")) {
			return false;
		}
		String baseOrigin = allowedOrigin.substring(0, allowedOrigin.length() - 2);
		return origin.equals(baseOrigin) || origin.startsWith(baseOrigin + ":");
	}

	/**
	 * Create a new {@link Builder} for {@link DefaultServerTransportSecurityValidator}.
	 */
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private final List<String> allowedOrigins = new ArrayList<>();

		/**
		 * Add a single allowed origin pattern.
		 *
		 * @param origin origin to allow (e.g. {@code http://localhost:8080} or
		 * {@code http://example.com:*})
		 * @return this builder
		 */
		public Builder allowedOrigin(String origin) {
			this.allowedOrigins.add(origin);
			return this;
		}

		/**
		 * Add multiple allowed origin patterns.
		 *
		 * @param origins origins to allow
		 * @return this builder
		 */
		public Builder allowedOrigins(List<String> origins) {
			Assert.notNull(origins, "origins must not be null");
			this.allowedOrigins.addAll(origins);
			return this;
		}

		/**
		 * Build a new {@link DefaultServerTransportSecurityValidator}.
		 *
		 * @return a configured {@link DefaultServerTransportSecurityValidator} instance
		 */
		public DefaultServerTransportSecurityValidator build() {
			return new DefaultServerTransportSecurityValidator(allowedOrigins);
		}

	}

}