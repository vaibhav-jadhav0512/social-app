package com.user.info.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.info.service.model.UserInfo;
import com.user.info.service.model.UserInfoDto;
import com.user.info.service.repository.UserInfoRepo;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoRepo repo;

	@Override
	public UserInfo getUserInfo(String userName) {
		return repo.getUserInfo(userName);
	}

	@Override
	public int updateUserInfo(UserInfo userInfo) {
		return repo.updateUserInfo(userInfo);
	}

	@Override
	public List<UserInfoDto> getAllUsers() {
		return repo.getAllUsers();
	}

	@Override
	public void updateProfileImage(String userName, String url) {
		repo.updateProfileImage(userName, url);
	}

}
