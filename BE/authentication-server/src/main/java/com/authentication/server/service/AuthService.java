
package com.authentication.server.service;

import org.springframework.security.core.Authentication;

import com.authentication.server.model.UserInfo;
import com.authentication.server.model.dto.AuthResponseDto;
import com.authentication.server.model.dto.UserRegistrationDto;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

public interface AuthService {

	UserInfo findByUserName(String username);

	AuthResponseDto getJwtTokensAfterAuthentication(Authentication authentication, HttpServletResponse response);

	Object getAccessTokenUsingRefreshToken(String authorizationHeader);

	void revokeRefreshToken(String refreshToken);

	AuthResponseDto registerUser(@Valid UserRegistrationDto userRegistrationDto,
			HttpServletResponse httpServletResponse);

}
