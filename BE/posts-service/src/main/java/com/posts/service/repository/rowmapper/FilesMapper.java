package com.posts.service.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.posts.service.model.Files;

public class FilesMapper implements RowMapper<Files>
{
	@Override
	public Files mapRow(ResultSet rs, int rowNum) throws SQLException {
		Files files = new Files();
		files.setId(rs.getInt("id"));
		files.setImageUrl(rs.getString("url"));
		return files;

	}
}