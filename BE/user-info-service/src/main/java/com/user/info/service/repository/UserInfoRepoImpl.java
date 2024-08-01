package com.user.info.service.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.user.info.service.model.UserInfo;
import com.user.info.service.model.UserInfoDto;
import com.user.info.service.repository.mapper.UserInfoDtoMapper;
import com.user.info.service.repository.mapper.UserInfoMapper;
import com.user.info.service.repository.sql.UserInfoQueries;

@Repository
public class UserInfoRepoImpl implements UserInfoRepo {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Override
	public UserInfo getUserInfo(String userName) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		return template.queryForObject(UserInfoQueries.GET_USER_INFO, paramMap, new UserInfoMapper());
	}

	@Override
	public int updateUserInfo(UserInfo userInfo) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userInfo.getUserName());
		paramMap.put("fullName", userInfo.getFullName());
		paramMap.put("bio", userInfo.getBio());
		paramMap.put("mobile", userInfo.getMobile());
		paramMap.put("profileImage", userInfo.getProfileImage());
		return template.update(UserInfoQueries.UPDATE_USER_INFO, paramMap);
	}

	@Override
	public List<UserInfoDto> getAllUsers() {
		return template.query(UserInfoQueries.GET_ALL_USERS, new UserInfoDtoMapper());
	}

}
