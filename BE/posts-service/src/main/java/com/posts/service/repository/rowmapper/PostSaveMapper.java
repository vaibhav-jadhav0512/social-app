package com.posts.service.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.posts.service.model.dto.PostDto;

public class PostSaveMapper implements RowMapper<PostDto> {

	@Override
	public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		PostDto postDto = new PostDto();
		postDto.setPostId(rs.getInt("post_id"));
		postDto.setUserName(rs.getString("user_name"));
		postDto.setProfileImage(rs.getString("profile_image"));
		return postDto;
	}
}