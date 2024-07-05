
package com.authentication.server.service;

import org.springframework.security.core.Authentication;

import com.authentication.server.model.UserInfo;
import com.authentication.server.model.dto.AuthResponseDto;

public interface AuthService {

	UserInfo findByUserName(String username);

	AuthResponseDto getJwtTokensAfterAuthentication(Authentication authentication);

}
