package io.modelcontextprotocol.server.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.modelcontextprotocol.util.Assert;

public class DefaultServerTransportSecurityValidator implements ServerTransportSecurityValidator {

	private static final String ORIGIN_HEADER = "Origin";

	private static final ServerTransportSecurityException INVALID_ORIGIN =
			new ServerTransportSecurityException(403, "Invalid Origin header");

	private final List<String> allowedOrigins;

	public DefaultServerTransportSecurityValidator(List<String> allowedOrigins) {
		Assert.notNull(allowedOrigins, "allowedOrigins must not be null");
		this.allowedOrigins = allowedOrigins;
	}

	@Override
	public void validateHeaders(Map<String, List<String>> requestHeaders) throws ServerTransportSecurityException {
		for (Map.Entry<String, List<String>> header : requestHeaders.entrySet()) {
			String headerName = header.getKey();
			if (ORIGIN_HEADER.equalsIgnoreCase(headerName)) {
				List<String> originHeaderValues = header.getValue();
				if (originHeaderValues != null && !originHeaderValues.isEmpty()) {
					String origin = originHeaderValues.get(0);
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

		for (String allowedOrigin : allowedOrigins) {
			if (allowedOrigin.equals(origin)) {
				return;
			}
			if (allowedOrigin.endsWith(":*")) {
				String allowedOriginWithoutWildcard = allowedOrigin.substring(0, allowedOrigin.length() - 2);
				if (origin.equals(allowedOriginWithoutWildcard) || origin.startsWith(allowedOriginWithoutWildcard + ":")) {
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