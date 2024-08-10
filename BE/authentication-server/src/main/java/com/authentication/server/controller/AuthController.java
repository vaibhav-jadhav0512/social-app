package com.authentication.server.controller;

import java.util.List;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.server.model.UserInfo;
import com.authentication.server.model.dto.ConnValidationResponse;
import com.authentication.server.model.dto.UserRegistrationDto;
import com.authentication.server.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
@CrossOrigin(origins = "http://3.111.34.33:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}, allowCredentials = "true")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/sign-in")
	public ResponseEntity<?> authenticateUser(Authentication authentication, HttpServletResponse response) {
		return ResponseEntity.ok(authService.getJwtTokensAfterAuthentication(authentication, response));
	}

	@PreAuthorize("hasAuthority('SCOPE_REFRESH_TOKEN')")
	@PostMapping("/refresh-token")
	public ResponseEntity<?> getAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		return ResponseEntity.ok(authService.getAccessTokenUsingRefreshToken(authorizationHeader));
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto,
			BindingResult bindingResult, HttpServletResponse httpServletResponse) {
		log.info("[AuthController:registerUser]Signup Process Started for user:{}", userRegistrationDto.userName());
		if (bindingResult.hasErrors()) {
			List<String> errorMessage = bindingResult.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
			log.error("[AuthController:registerUser]Errors in user:{}", errorMessage);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
		}
		return ResponseEntity.ok(authService.registerUser(userRegistrationDto, httpServletResponse));
	}

	@PreAuthorize("hasAuthority('SCOPE_READ')")
	@GetMapping("/validate-token")
	public ResponseEntity<ConnValidationResponse> validateGet(Authentication authentication) {
		return ResponseEntity.ok(ConnValidationResponse.builder().username(authentication.getName())
				.authorities(authentication.getAuthorities()).isAuthenticated(true).build());
	}

	@GetMapping("/user")
	public ResponseEntity<UserInfo> getUser(Authentication authentication) {
		return new ResponseEntity<>(authService.findByUserName(authentication.getName()), HttpStatus.OK);

	}
//Test
}
