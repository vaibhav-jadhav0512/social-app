package com.posts.service.config.validator;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenValidationFilter extends OncePerRequestFilter {
	@Autowired
	private RestTemplate restTemplate;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = extractToken(request);

		if (token != null && validateToken(token)) {
			Authentication auth = new UsernamePasswordAuthenticationToken(token, null);
			SecurityContextHolder.getContext().setAuthentication(auth);
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return;
		}

		filterChain.doFilter(request, response);
	}

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	private boolean validateToken(String token) {
		String url = "http://authentication-server:8080/auth/validate-token";
		try {
			ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, getRequestEntity(token),
					Object.class);
			Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
			return (boolean) responseBody.get("authenticated");
		} catch (Exception e) {
			return false;
		}
	}

	private HttpEntity<?> getRequestEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		return new HttpEntity<>(headers);
	}
}

