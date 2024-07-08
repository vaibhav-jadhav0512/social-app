package com.authentication.server.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.server.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/sign-in")
	public ResponseEntity<?> authenticateUser(Authentication authentication, HttpServletResponse response) {
		return ResponseEntity.ok(authService.getJwtTokensAfterAuthentication(authentication, response));
	}

//	@PreAuthorize("hasAuthority('SCOPE_REFRESH_TOKEN')")
	@PostMapping("/refresh-token")
	public ResponseEntity<?> getAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		return ResponseEntity.ok(authService.getAccessTokenUsingRefreshToken(authorizationHeader));
	}

}
