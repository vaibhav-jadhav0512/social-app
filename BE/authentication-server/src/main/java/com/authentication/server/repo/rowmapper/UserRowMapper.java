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
		user.setFirstName(rs.getString("first_name"));
		user.setLastName(rs.getString("last_name"));
		user.setPassword(rs.getString("password"));
		user.setMobile(rs.getString("mobile"));
		user.setRoles(rs.getString("roles"));
		return user;
	}

}
