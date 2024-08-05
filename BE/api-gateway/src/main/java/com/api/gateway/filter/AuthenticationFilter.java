package com.api.gateway.filter;

import java.util.Map;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	private final RestTemplate restTemplate;

	public AuthenticationFilter(RestTemplate restTemplate) {
		super(Config.class);
		this.restTemplate = restTemplate;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String path = exchange.getRequest().getURI().getPath();
			if (isPublicEndpoint(path)) {
				return chain.filter(exchange);
			}
			String token = exchange.getRequest().getHeaders().getFirst("Authorization");
			boolean isValidToken = validateToken(token);
			if (!isValidToken) {
				exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
				return exchange.getResponse().setComplete();
			}

			return chain.filter(exchange);
		};
	}
	private boolean isPublicEndpoint(String path) {
		return path.startsWith("/auth");
	}
	private boolean validateToken(String token) {
		String url = "http://13.232.173.254/auth/validate-token";
		try {
			ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(token),
					Object.class);
			Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
			return (boolean) responseBody.get("authenticated");
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw new RuntimeException("JWT Parse error");
		}
	}

	private HttpEntity<?> getRequestEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		return new HttpEntity<>(headers);
	}

	public static class Config {
	}
}
