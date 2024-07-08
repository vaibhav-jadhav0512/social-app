package com.authentication.server.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.authentication.server.config.jwt.JwtTokenGenerator;
import com.authentication.server.model.UserInfo;
import com.authentication.server.model.dto.AuthResponseDto;
import com.authentication.server.model.dto.RefreshTokenDto;
import com.authentication.server.model.dto.UserRegistrationDto;
import com.authentication.server.model.enums.TokenType;
import com.authentication.server.model.mapper.UserInfoMapper;
import com.authentication.server.repo.AuthRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
	private final UserInfoMapper userInfoMapper;

	@Override
	public UserInfo findByUserName(String username) {
		return repository.findByUserName(username);
	}

	@Override
	public AuthResponseDto getJwtTokensAfterAuthentication(Authentication authentication,
			HttpServletResponse response) {
		try {
			var userInfoEntity = repository.findByUserName(authentication.getName());

			String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
			String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);
			saveUserRefreshToken(userInfoEntity, refreshToken);
			createRefreshTokenCookie(response, refreshToken);
			log.info("[AuthService:userSignInAuth] Access token for user:{}, has been generated",
					userInfoEntity.getUserName());
			return AuthResponseDto.builder().accessToken(accessToken).accessTokenExpiry(15 * 60)
					.userName(userInfoEntity.getUserName()).tokenType(TokenType.Bearer).build();
		} catch (Exception e) {
			log.error("[AuthService:userSignInAuth]Exception while authenticating the user due to :" + e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
		}
	}

	private Cookie createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
		Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(true);
		refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60);
		response.addCookie(refreshTokenCookie);
		return refreshTokenCookie;
	}

	private void saveUserRefreshToken(UserInfo userInfoEntity, String refreshToken) {
		var refreshTokenEntity = RefreshTokenDto.builder().userInfo(userInfoEntity).refreshToken(refreshToken)
				.revoked(false).build();
		repository.saveRefreshToken(refreshTokenEntity);

	}

	@Override
	public Object getAccessTokenUsingRefreshToken(String authorizationHeader) {
		if (!authorizationHeader.startsWith(TokenType.Bearer.name())) {
			return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please verify your token type");
		}

		final String refreshToken = authorizationHeader.substring(7);

		RefreshTokenDto refreshTokenDto = repository.findByRefreshToken(refreshToken);

		UserInfo userInfoEntity = refreshTokenDto.getUserInfo();
		Authentication authentication = createAuthenticationObject(userInfoEntity);
		String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

		return AuthResponseDto.builder().accessToken(accessToken).accessTokenExpiry(5 * 60)
				.userName(userInfoEntity.getUserName()).tokenType(TokenType.Bearer).build();
	}

	private Authentication createAuthenticationObject(UserInfo userInfoEntity) {
		String username = userInfoEntity.getUserName();
		String password = userInfoEntity.getPassword();
		String roles = userInfoEntity.getRoles();
		String[] roleArray = roles.split(",");
		GrantedAuthority[] authorities = Arrays.stream(roleArray).map(role -> (GrantedAuthority) role::trim)
				.toArray(GrantedAuthority[]::new);
		return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
	}

	@Override
	public void revokeRefreshToken(String refreshToken) {
		repository.revokeRefreshToken(refreshToken);
	}

	@Override
	public AuthResponseDto registerUser(UserRegistrationDto userRegistrationDto,
			HttpServletResponse httpServletResponse) {
		try {
			log.info("[AuthService:registerUser]User Registration Started with :::{}", userRegistrationDto);
			UserInfo user = repository.findByUserName(userRegistrationDto.userName());
			if (user != null) {
				throw new Exception("User Already Exist");
			}
			UserInfo userDetails = userInfoMapper.convertToEntity(userRegistrationDto);
			Authentication authentication = createAuthenticationObject(userDetails);
			String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
			String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);
			repository.saveUser(userDetails);
			saveUserRefreshToken(userDetails, refreshToken);
			createRefreshTokenCookie(httpServletResponse, refreshToken);
			log.info("[AuthService:registerUser] User:{} Successfully registered", userDetails.getUserName());
			return AuthResponseDto.builder().accessToken(accessToken).accessTokenExpiry(5 * 60)
					.userName(userDetails.getUserName()).tokenType(TokenType.Bearer).build();
		} catch (Exception e) {
			log.error("[AuthService:registerUser]Exception while registering the user due to :" + e.getMessage());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}
}
