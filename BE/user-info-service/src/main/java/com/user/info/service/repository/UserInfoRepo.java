package com.user.info.service.repository;

import java.util.List;

import com.user.info.service.model.UserInfo;
import com.user.info.service.model.UserInfoDto;

public interface UserInfoRepo {

	UserInfo getUserInfo(String userName);

	int updateUserInfo(UserInfo userInfo);

	List<UserInfoDto> getAllUsers();

	void updateProfileImage(String userName, String url);

}
