package com.authentication.server.model.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ConnValidationResponse {
	private boolean isAuthenticated;
	private String username;
	private Collection<? extends GrantedAuthority> authorities;

}
