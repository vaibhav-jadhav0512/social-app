package com.authentication.server.config.jwt;

import java.time.Instant;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.authentication.server.config.user.UserInfoConfig;
import com.authentication.server.model.UserInfo;
import com.authentication.server.service.AuthService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

	public String getUserName(Jwt jwtToken) {
		return jwtToken.getSubject();
	}

	public boolean isTokenValid(Jwt jwtToken, UserDetails userDetails) {
		final String userName = getUserName(jwtToken);
		boolean isTokenExpired = getIfTokenIsExpired(jwtToken);
		boolean isTokenUserSameAsDatabase = userName.equals(userDetails.getUsername());
		return !isTokenExpired && isTokenUserSameAsDatabase;

	}

	private boolean getIfTokenIsExpired(Jwt jwtToken) {
		return Objects.requireNonNull(jwtToken.getExpiresAt()).isBefore(Instant.now());
	}

	@Autowired
	private AuthService authService;

	public UserDetails userDetails(String userName) {
		UserInfo userInfo = authService.findByUserName(userName);
		if (userInfo != null) {
			return new UserInfoConfig(userInfo);
		} else {
			throw new UsernameNotFoundException("UserName: " + userName + " does not exist");
		}
	}
}