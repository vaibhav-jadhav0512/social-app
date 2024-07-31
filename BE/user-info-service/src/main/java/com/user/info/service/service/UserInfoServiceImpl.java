package com.user.info.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.info.service.model.UserInfo;
import com.user.info.service.repository.UserInfoRepo;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoRepo repo;

	@Override
	public UserInfo getUserInfo(String userName) {
		return repo.getUserInfo(userName);
	}


}
