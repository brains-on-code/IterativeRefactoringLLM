package io.modelcontextprotocol.server.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.modelcontextprotocol.util.Assert;

public class DefaultServerTransportSecurityValidator implements ServerTransportSecurityValidator {

	private static final String ORIGIN_HEADER = "Origin";
	private static final String WILDCARD_SUFFIX = ":*";

	private static final ServerTransportSecurityException INVALID_ORIGIN =
			new ServerTransportSecurityException(403, "Invalid Origin header");

	private final List<String> allowedOrigins;

	public DefaultServerTransportSecurityValidator(List<String> allowedOrigins) {
		Assert.notNull(allowedOrigins, "allowedOrigins must not be null");
		this.allowedOrigins = allowedOrigins;
	}

	@Override
	public void validateHeaders(Map<String, List<String>> headers) throws ServerTransportSecurityException {
		String origin = extractOriginHeader(headers);
		if (origin != null) {
			validateOrigin(origin);
		}
	}

	private String extractOriginHeader(Map<String, List<String>> headers) {
		for (Map.Entry<String, List<String>> headerEntry : headers.entrySet()) {
			if (ORIGIN_HEADER.equalsIgnoreCase(headerEntry.getKey())) {
				List<String> values = headerEntry.getValue();
				if (values == null || values.isEmpty()) {
					return null;
				}
				return values.get(0);
			}
		}
		return null;
	}

	protected void validateOrigin(String origin) throws ServerTransportSecurityException {
		if (origin == null || origin.isBlank()) {
			return;
		}

		for (String allowedOrigin : allowedOrigins) {
			if (isExactMatch(allowedOrigin, origin) || isWildcardMatch(allowedOrigin, origin)) {
				return;
			}
		}

		throw INVALID_ORIGIN;
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

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private final List<String> allowedOrigins = new ArrayList<>();

		public Builder allowedOrigin(String origin) {
			this.allowedOrigins.add(origin);
			return this;
		}

		public Builder allowedOrigins(List<String> origins) {
			Assert.notNull(origins, "origins must not be null");
			this.allowedOrigins.addAll(origins);
			return this;
		}

		public DefaultServerTransportSecurityValidator build() {
			return new DefaultServerTransportSecurityValidator(allowedOrigins);
		}

	}

}