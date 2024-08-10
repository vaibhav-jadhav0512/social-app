package com.posts.service.config.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {

	private final RestTemplate restTemplate;

	public JwtTokenValidatorFilter(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (token != null && token.startsWith("Bearer ")) {
			String url = "http://15.207.221.209:8888/auth/validate-token";
			try {
				ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
						getRequestEntity(token), Object.class);
				System.out.println(responseEntity);
				Map<String, Object> userDetails = (Map<String, Object>) responseEntity.getBody();
				String username = (String) userDetails.get("username");
				List<SimpleGrantedAuthority> authorities = Collections.emptyList();
				SecurityContextHolder.getContext()
						.setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
				filterChain.doFilter(request, response);
			} catch (HttpClientErrorException | HttpServerErrorException e) {
				throw new RuntimeException("Error");
			}
		}
	}

	private HttpEntity<?> getRequestEntity(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		return new HttpEntity<>(headers);
	}
}