package com.authentication.server.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.authentication.server.config.jwt.JwtAccessTokenFilter;
import com.authentication.server.config.jwt.JwtRefreshTokenFilter;
import com.authentication.server.config.jwt.JwtTokenUtils;
import com.authentication.server.config.user.UserInfoManagerConfig;
import com.authentication.server.repo.AuthRepository;
import com.authentication.server.service.LogoutHandlerService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
	private final UserInfoManagerConfig userInfoManagerConfig;
	private final RSAKeyRecord rsaKeyRecord;
	private final JwtTokenUtils jwtTokenUtils;
	private final AuthRepository service;
	private final LogoutHandlerService logoutHandlerService;

	@Order(1)
	@Bean
	public SecurityFilterChain signInSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.securityMatcher(new AntPathRequestMatcher("/sign-in/**"))
				.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.userDetailsService(userInfoManagerConfig)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(ex -> {
					ex.authenticationEntryPoint((request, response, authException) -> response
							.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()));
				}).httpBasic(withDefaults()).build();
	}

	@Order(2)
	@Bean
	public SecurityFilterChain apiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.securityMatcher(new AntPathRequestMatcher("/api/**")).csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtAccessTokenFilter(rsaKeyRecord, jwtTokenUtils),
						UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(ex -> {
					log.error("[SecurityConfig:apiSecurityFilterChain] Exception due to :{}", ex);
					ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
					ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
				}).httpBasic(withDefaults()).build();
	}

	@Order(3)
	@Bean
	public SecurityFilterChain refreshTokenSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.securityMatcher(new AntPathRequestMatcher("/refresh-token/**"))
				.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtRefreshTokenFilter(rsaKeyRecord, jwtTokenUtils, service),
						UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling(ex -> {
					log.error("[SecurityConfig:refreshTokenSecurityFilterChain] Exception due to :{}", ex);
					ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
					ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
				}).httpBasic(withDefaults()).build();
	}

	@Order(4)
	@Bean
	public SecurityFilterChain logoutSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.securityMatcher(new AntPathRequestMatcher("/logout/**"))
				.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new JwtAccessTokenFilter(rsaKeyRecord, jwtTokenUtils),
						UsernamePasswordAuthenticationFilter.class)
				.logout(logout -> logout.logoutUrl("/logout").addLogoutHandler(logoutHandlerService)
						.logoutSuccessHandler(
								((request, response, authentication) -> SecurityContextHolder.clearContext())))
				.exceptionHandling(ex -> {
					log.error("[SecurityConfig:logoutSecurityFilterChain] Exception due to :{}", ex);
					ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint());
					ex.accessDeniedHandler(new BearerTokenAccessDeniedHandler());
				}).build();
	}
}
