package com.authentication.server.model.dto;

public record UserRegistrationDto(String userName, String mobile, String email, String password, 
		String name) {
}
