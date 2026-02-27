package io.modelcontextprotocol.server.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.modelcontextprotocol.util.Assert;

public class Class1 implements ServerTransportSecurityValidator {

	private static final String ORIGIN_HEADER = "Origin";
	private static final String WILDCARD_SUFFIX = ":*";

	private static final ServerTransportSecurityException INVALID_ORIGIN_EXCEPTION =
			new ServerTransportSecurityException(403, "Invalid Origin header");

	private final List<String> allowedOrigins;

	public Class1(List<String> allowedOrigins) {
		Assert.notNull(allowedOrigins, "allowedOrigins must not be null");
		this.allowedOrigins = allowedOrigins;
	}

	@Override
	public void method1(Map<String, List<String>> headers) throws ServerTransportSecurityException {
		List<String> originValues = getHeaderValues(headers, ORIGIN_HEADER);
		if (originValues == null || originValues.isEmpty()) {
			return;
		}
		validateOrigin(originValues.get(0));
	}

	private List<String> getHeaderValues(Map<String, List<String>> headers, String headerName) {
		return headers.entrySet().stream()
				.filter(entry -> headerName.equalsIgnoreCase(entry.getKey()))
				.map(Map.Entry::getValue)
				.findFirst()
				.orElse(null);
	}

	protected void validateOrigin(String origin) throws ServerTransportSecurityException {
		if (origin == null || origin.isBlank()) {
			return;
		}

		if (isOriginAllowed(origin)) {
			return;
		}

		throw INVALID_ORIGIN_EXCEPTION;
	}

	private boolean isOriginAllowed(String origin) {
		for (String allowedOrigin : allowedOrigins) {
			if (isExactMatch(allowedOrigin, origin) || isWildcardMatch(allowedOrigin, origin)) {
				return true;
			}
		}
		return false;
	}

	private boolean isExactMatch(String allowedOrigin, String origin) {
		return allowedOrigin.equals(origin);
	}

	private boolean isWildcardMatch(String allowedOrigin, String origin) {
		if (!allowedOrigin.endsWith(WILDCARD_SUFFIX)) {
			return false;
		}

		String baseOrigin = allowedOrigin.substring(0, allowedOrigin.length() - WILDCARD_SUFFIX.length());
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