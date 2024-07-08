package com.authentication.server.model.dto;

import com.authentication.server.model.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefreshTokenDto {
	private int id;
	private String refreshToken;
	private boolean revoked;
	private UserInfo userInfo;
}
