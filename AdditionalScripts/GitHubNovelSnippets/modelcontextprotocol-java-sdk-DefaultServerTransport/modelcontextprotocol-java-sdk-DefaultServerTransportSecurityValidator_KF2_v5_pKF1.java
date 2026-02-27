package io.modelcontextprotocol.server.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.modelcontextprotocol.util.Assert;

public class DefaultServerTransportSecurityValidator implements ServerTransportSecurityValidator {

	private static final String ORIGIN_HEADER_NAME = "Origin";

	private static final ServerTransportSecurityException INVALID_ORIGIN_EXCEPTION =
			new ServerTransportSecurityException(403, "Invalid Origin header");

	private final List<String> allowedOriginPatterns;

	public DefaultServerTransportSecurityValidator(List<String> allowedOriginPatterns) {
		Assert.notNull(allowedOriginPatterns, "allowedOriginPatterns must not be null");
		this.allowedOriginPatterns = allowedOriginPatterns;
	}

	@Override
	public void validateHeaders(Map<String, List<String>> requestHeaders) throws ServerTransportSecurityException {
		for (Map.Entry<String, List<String>> headerEntry : requestHeaders.entrySet()) {
			String headerName = headerEntry.getKey();
			if (ORIGIN_HEADER_NAME.equalsIgnoreCase(headerName)) {
				List<String> originHeaderValues = headerEntry.getValue();
				if (originHeaderValues != null && !originHeaderValues.isEmpty()) {
					String originHeaderValue = originHeaderValues.get(0);
					validateOrigin(originHeaderValue);
				}
				break;
			}
		}
	}

	protected void validateOrigin(String origin) throws ServerTransportSecurityException {
		if (origin == null || origin.isBlank()) {
			return;
		}

		for (String allowedOriginPattern : allowedOriginPatterns) {
			if (allowedOriginPattern.equals(origin)) {
				return;
			}
			if (allowedOriginPattern.endsWith(":*")) {
				String allowedOriginBase = allowedOriginPattern.substring(0, allowedOriginPattern.length() - 2);
				if (origin.equals(allowedOriginBase) || origin.startsWith(allowedOriginBase + ":")) {
					return;
				}
			}
		}

		throw INVALID_ORIGIN_EXCEPTION;
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