package com.authentication.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	private int userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mobile;
	private String roles;
}
