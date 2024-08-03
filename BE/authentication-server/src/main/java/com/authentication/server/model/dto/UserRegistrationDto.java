package com.authentication.server.model.dto;

public record UserRegistrationDto(String userName, String email, String password, String name) {
}
