package com.user.info.service.service;

import java.util.List;

import com.user.info.service.model.UserInfo;
import com.user.info.service.model.UserInfoDto;

public interface UserInfoService {

	UserInfo getUserInfo(String userName);

	int updateUserInfo(UserInfo userInfo);

	List<UserInfoDto> getAllUsers();

}
