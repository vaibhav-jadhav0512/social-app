package com.authentication.server.config.jwt;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.authentication.server.config.RSAKeyRecord;
import com.authentication.server.model.dto.RefreshTokenDto;
import com.authentication.server.repo.AuthRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtRefreshTokenFilter extends OncePerRequestFilter {

	private final RSAKeyRecord rsaKeyRecord;
	private final JwtTokenUtils jwtTokenUtils;
	private final AuthRepository service;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			log.info("[JwtRefreshTokenFilter:doFilterInternal] :: Started ");
			log.info("[JwtRefreshTokenFilter:doFilterInternal]Filtering the Http Request:{}", request.getRequestURI());
			final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			JwtDecoder jwtDecoder = NimbusJwtDecoder.withPublicKey(rsaKeyRecord.rsaPublicKey()).build();
			if (!authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			final String token = authHeader.substring(7);
			final Jwt jwtRefreshToken = jwtDecoder.decode(token);
			final String userName = jwtTokenUtils.getUserName(jwtRefreshToken);
			if (!userName.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
				RefreshTokenDto userByToken = service.findByRefreshToken(jwtRefreshToken.getTokenValue());
				boolean isRefreshTokenValidInDatabase = userByToken.isRevoked();
				log.info("isRefreshTokenValidInDatabase:{}", isRefreshTokenValidInDatabase);
				UserDetails userDetails = jwtTokenUtils.userDetails(userName);
				if (jwtTokenUtils.isTokenValid(jwtRefreshToken, userDetails) && !isRefreshTokenValidInDatabase) {
					log.info("Validated");
					SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
					UsernamePasswordAuthenticationToken createdToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					createdToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					securityContext.setAuthentication(createdToken);
					SecurityContextHolder.setContext(securityContext);
				} else {
					throw new InvalidBearerTokenException("Token is invalid");
				}
			}
			log.info("[JwtRefreshTokenFilter:doFilterInternal] Completed");
			filterChain.doFilter(request, response);
		} catch (JwtValidationException jwtValidationException) {
			log.error("[JwtRefreshTokenFilter:doFilterInternal] Exception due to :{}",
					jwtValidationException.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, jwtValidationException.getMessage());
		}
	}
}
