package com.user.info.service.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.user.info.service.model.UserInfoDto;

public class UserInfoDtoMapper implements RowMapper<UserInfoDto> {
	@Override
	public UserInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserInfoDto userInfo = new UserInfoDto();
		userInfo.setUserName(rs.getString("user_name"));
		userInfo.setFullName(rs.getString("full_name"));
		userInfo.setProfileImage(rs.getString("profile_image"));
		return userInfo;
	}

}