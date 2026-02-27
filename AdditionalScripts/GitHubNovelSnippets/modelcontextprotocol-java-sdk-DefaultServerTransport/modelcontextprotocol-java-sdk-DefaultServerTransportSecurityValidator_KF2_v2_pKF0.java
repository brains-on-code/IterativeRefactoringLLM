package io.modelcontextprotocol.server.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.modelcontextprotocol.util.Assert;

public class DefaultServerTransportSecurityValidator implements ServerTransportSecurityValidator {

	private static final String ORIGIN_HEADER = "Origin";
	private static final String WILDCARD_SUFFIX = ":*";
	private static final int FORBIDDEN_STATUS = 403;
	private static final ServerTransportSecurityException INVALID_ORIGIN =
			new ServerTransportSecurityException(FORBIDDEN_STATUS, "Invalid Origin header");

	private final List<String> allowedOrigins;

	public DefaultServerTransportSecurityValidator(List<String> allowedOrigins) {
		Assert.notNull(allowedOrigins, "allowedOrigins must not be null");
		this.allowedOrigins = allowedOrigins;
	}

	@Override
	public void validateHeaders(Map<String, List<String>> headers) throws ServerTransportSecurityException {
		if (headers == null || headers.isEmpty()) {
			return;
		}

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

	protected void validateOrigin(String origin) throws ServerTransportSecurityException {
		if (origin == null || origin.isBlank()) {
			return;
		}

		for (String allowed : allowedOrigins) {
			if (isExactMatch(allowed, origin) || isWildcardMatch(allowed, origin)) {
				return;
			}
		}

		throw INVALID_ORIGIN;
	}

	private boolean isExactMatch(String allowed, String origin) {
		return allowed.equals(origin);
	}

	private boolean isWildcardMatch(String allowed, String origin) {
		if (!allowed.endsWith(WILDCARD_SUFFIX)) {
			return false;
		}

		String baseOrigin = allowed.substring(0, allowed.length() - WILDCARD_SUFFIX.length());
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