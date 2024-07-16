package com.posts.service.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.posts.service.model.FileMetadata;

public class FileMapper implements RowMapper<FileMetadata> {
	@Override
	public FileMetadata mapRow(ResultSet rs, int rowNum) throws SQLException {
		FileMetadata fileMetadata = new FileMetadata();
		fileMetadata.setId(rs.getInt("id"));
		fileMetadata.setUrl(rs.getString("url"));
		return fileMetadata;

	}
}