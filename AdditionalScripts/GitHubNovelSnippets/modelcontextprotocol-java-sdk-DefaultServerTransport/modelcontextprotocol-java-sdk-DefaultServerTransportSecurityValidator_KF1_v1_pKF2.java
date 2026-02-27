package io.modelcontextprotocol.server.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.modelcontextprotocol.util.Assert;

/**
 * Validates the Origin header of incoming requests against a configured
 * list of allowed origins.
 *
 * Supports exact matches and wildcard port patterns such as
 * {@code "scheme://host:*"}.
 */
public class Class1 implements ServerTransportSecurityValidator {

	private static final String ORIGIN_HEADER = "Origin";

	private static final ServerTransportSecurityException INVALID_ORIGIN_EXCEPTION =
			new ServerTransportSecurityException(403, "Invalid Origin header");

	private final List<String> allowedOrigins;

	/**
	 * Create a new validator with the given list of allowed origins.
	 *
	 * @param allowedOrigins list of allowed origin strings (must not be {@code null})
	 */
	public Class1(List<String> allowedOrigins) {
		Assert.notNull(allowedOrigins, "allowedOrigins must not be null");
		this.allowedOrigins = allowedOrigins;
	}

	@Override
	public void method1(Map<String, List<String>> headers) throws ServerTransportSecurityException {
		for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
			if (ORIGIN_HEADER.equalsIgnoreCase(entry.getKey())) {
				List<String> values = entry.getValue();
				if (values != null && !values.isEmpty()) {
					validateOrigin(values.get(0));
				}
				break;
			}
		}
	}

	/**
	 * Validate a single Origin header value against the configured allowed origins.
	 *
	 * @param origin the Origin header value
	 * @throws ServerTransportSecurityException if the origin is not allowed
	 */
	protected void validateOrigin(String origin) throws ServerTransportSecurityException {
		if (origin == null || origin.isBlank()) {
			return;
		}

		for (String allowedOrigin : allowedOrigins) {
			if (allowedOrigin.equals(origin)) {
				return;
			}
			else if (allowedOrigin.endsWith(":*")) {
				String base = allowedOrigin.substring(0, allowedOrigin.length() - 2);
				if (origin.equals(base) || origin.startsWith(base + ":")) {
					return;
				}
			}
		}

		throw INVALID_ORIGIN_EXCEPTION;
	}

	/**
	 * Create a new builder for {@link Class1}.
	 */
	public static Class2 method3() {
		return new Class2();
	}

	/**
	 * Builder for {@link Class1}.
	 */
	public static class Class2 {

		private final List<String> allowedOrigins = new ArrayList<>();

		/**
		 * Add a single allowed origin.
		 *
		 * @param origin the origin to allow
		 * @return this builder
		 */
		public Class2 method4(String origin) {
			this.allowedOrigins.add(origin);
			return this;
		}

		/**
		 * Add multiple allowed origins.
		 *
		 * @param origins list of origins to allow (must not be {@code null})
		 * @return this builder
		 */
		public Class2 method5(List<String> origins) {
			Assert.notNull(origins, "origins must not be null");
			this.allowedOrigins.addAll(origins);
			return this;
		}

		/**
		 * Build a {@link Class1} instance with the configured allowed origins.
		 *
		 * @return a new {@link Class1}
		 */
		public Class1 method6() {
			return new Class1(allowedOrigins);
		}

	}

}