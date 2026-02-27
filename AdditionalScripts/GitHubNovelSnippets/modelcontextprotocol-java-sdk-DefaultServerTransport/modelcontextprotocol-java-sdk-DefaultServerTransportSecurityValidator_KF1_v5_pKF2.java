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

	public Class1(List<String> allowedOrigins) {
		Assert.notNull(allowedOrigins, "allowedOrigins must not be null");
		this.allowedOrigins = allowedOrigins;
	}

	@Override
	public void method1(Map<String, List<String>> headers) throws ServerTransportSecurityException {
		List<String> originHeaderValues = headers.entrySet().stream()
				.filter(entry -> ORIGIN_HEADER.equalsIgnoreCase(entry.getKey()))
				.map(Map.Entry::getValue)
				.findFirst()
				.orElse(null);

		if (originHeaderValues == null || originHeaderValues.isEmpty()) {
			return;
		}

		validateOrigin(originHeaderValues.get(0));
	}

	protected void validateOrigin(String origin) throws ServerTransportSecurityException {
		if (origin == null || origin.isBlank()) {
			return;
		}

		for (String allowedOrigin : allowedOrigins) {
			if (isExactOriginMatch(allowedOrigin, origin) || isWildcardPortMatch(allowedOrigin, origin)) {
				return;
			}
		}

		throw INVALID_ORIGIN_EXCEPTION;
	}

	private boolean isExactOriginMatch(String allowedOrigin, String origin) {
		return allowedOrigin.equals(origin);
	}

	private boolean isWildcardPortMatch(String allowedOrigin, String origin) {
		if (!allowedOrigin.endsWith(":*")) {
			return false;
		}

		String baseOrigin = allowedOrigin.substring(0, allowedOrigin.length() - 2);
		return origin.equals(baseOrigin) || origin.startsWith(baseOrigin + ":");
	}

	public static Class2 method3() {
		return new Class2();
	}

	public static class Class2 {

		private final List<String> allowedOrigins = new ArrayList<>();

		public Class2 method4(String origin) {
			this.allowedOrigins.add(origin);
			return this;
		}

		public Class2 method5(List<String> origins) {
			Assert.notNull(origins, "origins must not be null");
			this.allowedOrigins.addAll(origins);
			return this;
		}

		public Class1 method6() {
			return new Class1(allowedOrigins);
		}

	}

}