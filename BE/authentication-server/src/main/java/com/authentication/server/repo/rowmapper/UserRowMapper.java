package com.authentication.server.repo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.authentication.server.model.UserInfo;

public class UserRowMapper implements RowMapper<UserInfo> {
	public UserRowMapper() {
	}

	@Override
	public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserInfo user = new UserInfo();
		user.setUserId(rs.getInt("user_id"));
		user.setUserName(rs.getString("user_name"));
		user.setPassword(rs.getString("password"));
		user.setRoles(rs.getString("roles"));
		user.setFullName(rs.getString("full_name"));
		user.setEmail(rs.getString("email"));
		return user;
	}

}
