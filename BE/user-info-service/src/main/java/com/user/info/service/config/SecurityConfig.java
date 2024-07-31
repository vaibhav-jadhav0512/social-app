package com.user.info.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	private RestTemplate restTemplate; // Inject RestTemplate

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.anyRequest().authenticated().and()
				.addFilterBefore(new JwtTokenValidatorFilter(restTemplate), BasicAuthenticationFilter.class);

		return http.build();
	}
}
