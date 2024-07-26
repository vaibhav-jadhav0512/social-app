package com.posts.service.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.posts.service.model.dto.PostDto;

public class PostFileMapper implements RowMapper<PostDto> {

	@Override
    public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        PostDto postDto = new PostDto();
		postDto.setId(rs.getInt("id"));
        postDto.setUserName(rs.getString("user_name"));
        postDto.setCaption(rs.getString("caption"));
        postDto.setLocation(rs.getString("location"));
        postDto.setTags(rs.getString("tags"));
		postDto.setCreatedAt(rs.getTimestamp("created_at"));
		return postDto;
	}
    }