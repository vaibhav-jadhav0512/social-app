package com.authentication.server.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
//	@PreAuthorize("hasAuthority('SCOPE_READ')")
	@GetMapping("/welcome-message")
	public ResponseEntity<String> getFirstWelcomeMessage(Authentication authentication) {
		return ResponseEntity.ok("Welcome to the JWT Tutorial:" + authentication.getName() + "with scope:"
				+ authentication.getAuthorities());
	}

	@PreAuthorize("hasRole('ROLE_USER')")
//	@PreAuthorize("hasAuthority('SCOPE_READ')")
	@GetMapping("/user-message")
	public ResponseEntity<String> getManagerData(Principal principal) {
		return ResponseEntity.ok("Manager::" + principal.getName());
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	@PreAuthorize("hasAuthority('SCOPE_WRITE')")
	@PostMapping("/admin-message")
	public ResponseEntity<String> getAdminData(@RequestParam("message") String message, Principal principal) {
		return ResponseEntity.ok("Admin::" + principal.getName() + " has this message:" + message);
	}
}
