package com.authentication.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.authentication.server.config.jwt.JwtTokenGenerator;
import com.authentication.server.model.UserInfo;
import com.authentication.server.model.dto.AuthResponseDto;
import com.authentication.server.model.enums.TokenType;
import com.authentication.server.repo.AuthRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthRepository repository;
	@Autowired
	private final JwtTokenGenerator jwtTokenGenerator;

	@Override
	public UserInfo findByUserName(String username) {
		return repository.findByUserName(username);
	}

	@Override
	public AuthResponseDto getJwtTokensAfterAuthentication(Authentication authentication) {
		try {
			var userInfoEntity = repository.findByUserName(authentication.getName());

			String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

			log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated",
					userInfoEntity.getUserName());
			return AuthResponseDto.builder().accessToken(accessToken).accessTokenExpiry(15 * 60)
					.userName(userInfoEntity.getUserName()).tokenType(TokenType.Bearer).build();
		} catch (Exception e) {
			log.error("[AuthService:userSignInAuth]Exception while authenticating the user due to :" + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
		}
	}
}
