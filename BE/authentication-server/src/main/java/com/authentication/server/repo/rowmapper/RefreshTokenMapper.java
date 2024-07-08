package com.authentication.server.repo.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.authentication.server.model.UserInfo;
import com.authentication.server.model.dto.RefreshTokenDto;

public class RefreshTokenMapper implements RowMapper<RefreshTokenDto> {
	public RefreshTokenMapper() {
	}

	@Override
	public RefreshTokenDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		RefreshTokenDto token = new RefreshTokenDto();
		token.setRevoked(rs.getBoolean("revoked"));
		token.setId(rs.getInt("id"));
		token.setRefreshToken(rs.getString("refresh_token"));
		UserInfo userInfo = new UserRowMapper().mapRow(rs, rowNum);
		token.setUserInfo(userInfo);
		return token;
	}

}