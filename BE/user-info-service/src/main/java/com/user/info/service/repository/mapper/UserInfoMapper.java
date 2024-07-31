package com.user.info.service.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.user.info.service.model.UserInfo;

public class UserInfoMapper implements RowMapper<UserInfo> {
	@Override
	public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(rs.getString("user_name"));
		userInfo.setFullName(rs.getString("full_name"));
		userInfo.setBio(rs.getString("bio"));
		userInfo.setMobile(rs.getString("mobile"));
		userInfo.setProfileImage(rs.getString("profile_image"));
		return userInfo;
	}

}
