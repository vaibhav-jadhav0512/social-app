package com.authentication.server.model;

import java.util.List;

import com.authentication.server.model.dto.RefreshTokenDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	private int userId;
	private String userName;
	private String name;
	private String email;
	@JsonIgnore
	private String password;
	private String mobile;
	private String roles;
	private List<RefreshTokenDto> refreshTokens;
}
