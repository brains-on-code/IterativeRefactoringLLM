

package io.modelcontextprotocol.server.transport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.modelcontextprotocol.util.Assert;


public class DefaultServerTransportSecurityValidator implements ServerTransportSecurityValidator {

	private static final String ORIGIN_HEADER = "Origin";

	private static final ServerTransportSecurityException INVALID_ORIGIN = new ServerTransportSecurityException(403,
			"Invalid Origin header");

	private final List<String> allowedOrigins;


	public DefaultServerTransportSecurityValidator(List<String> allowedOrigins) {
		Assert.notNull(allowedOrigins, "allowedOrigins must not be null");
		this.allowedOrigins = allowedOrigins;
	}

	@Override
	public void validateHeaders(Map<String, List<String>> headers) throws ServerTransportSecurityException {
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


	protected void validateOrigin(String origin) throws ServerTransportSecurityException {
		if (origin == null || origin.isBlank()) {
			return;
		}

		for (String allowed : allowedOrigins) {
			if (allowed.equals(origin)) {
				return;
			}
			else if (allowed.endsWith(":*")) {
				String baseOrigin = allowed.substring(0, allowed.length() - 2);
				if (origin.equals(baseOrigin) || origin.startsWith(baseOrigin + ":")) {
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
