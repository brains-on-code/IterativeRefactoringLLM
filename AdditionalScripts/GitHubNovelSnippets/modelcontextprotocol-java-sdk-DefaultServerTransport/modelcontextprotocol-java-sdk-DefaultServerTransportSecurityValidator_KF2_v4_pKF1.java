package io.modelcontextprotocol.server.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.modelcontextprotocol.util.Assert;

public class DefaultServerTransportSecurityValidator implements ServerTransportSecurityValidator {

	private static final String ORIGIN_HEADER = "Origin";

	private static final ServerTransportSecurityException INVALID_ORIGIN =
			new ServerTransportSecurityException(403, "Invalid Origin header");

	private final List<String> allowedOriginPatterns;

	public DefaultServerTransportSecurityValidator(List<String> allowedOriginPatterns) {
		Assert.notNull(allowedOriginPatterns, "allowedOriginPatterns must not be null");
		this.allowedOriginPatterns = allowedOriginPatterns;
	}

	@Override
	public void validateHeaders(Map<String, List<String>> requestHeaders) throws ServerTransportSecurityException {
		for (Map.Entry<String, List<String>> header : requestHeaders.entrySet()) {
			String headerName = header.getKey();
			if (ORIGIN_HEADER.equalsIgnoreCase(headerName)) {
				List<String> originValues = header.getValue();
				if (originValues != null && !originValues.isEmpty()) {
					String origin = originValues.get(0);
					validateOrigin(origin);
				}
				break;
			}
		}
	}

	protected void validateOrigin(String origin) throws ServerTransportSecurityException {
		if (origin == null || origin.isBlank()) {
			return;
		}

		for (String allowedPattern : allowedOriginPatterns) {
			if (allowedPattern.equals(origin)) {
				return;
			}
			if (allowedPattern.endsWith(":*")) {
				String allowedBaseOrigin = allowedPattern.substring(0, allowedPattern.length() - 2);
				if (origin.equals(allowedBaseOrigin) || origin.startsWith(allowedBaseOrigin + ":")) {
					return;
				}
			}
		}

		throw INVALID_ORIGIN;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private final List<String> allowedOriginPatterns = new ArrayList<>();

		public Builder allowedOrigin(String origin) {
			this.allowedOriginPatterns.add(origin);
			return this;
		}

		public Builder allowedOrigins(List<String> origins) {
			Assert.notNull(origins, "origins must not be null");
			this.allowedOriginPatterns.addAll(origins);
			return this;
		}

		public DefaultServerTransportSecurityValidator build() {
			return new DefaultServerTransportSecurityValidator(allowedOriginPatterns);
		}

	}

}