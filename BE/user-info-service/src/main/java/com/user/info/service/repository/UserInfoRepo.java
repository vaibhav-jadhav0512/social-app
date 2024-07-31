package com.user.info.service.repository;

import com.user.info.service.model.UserInfo;

public interface UserInfoRepo {

	UserInfo getUserInfo(String userName);

	int updateUserInfo(UserInfo userInfo);

}
