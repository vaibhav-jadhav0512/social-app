package com.user.info.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
	private String userName;
	private String fullName;
	private String bio;
	private String mobile;
	private String profileImage;
}
