package com.authentication.server.model.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.authentication.server.model.UserInfo;
import com.authentication.server.model.dto.UserRegistrationDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserInfoMapper {

	private final PasswordEncoder passwordEncoder;

	public UserInfo convertToEntity(UserRegistrationDto userRegistrationDto) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userRegistrationDto.userName());
		userInfo.setEmail(userRegistrationDto.email());
		userInfo.setPassword(passwordEncoder.encode(userRegistrationDto.password()));
		userInfo.setFullName(userRegistrationDto.name());
		return userInfo;
	}
}
