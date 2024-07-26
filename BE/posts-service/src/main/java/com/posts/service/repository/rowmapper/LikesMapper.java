package com.posts.service.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.posts.service.model.Likes;

public class LikesMapper implements RowMapper<Likes> {
	@Override
	public Likes mapRow(ResultSet rs, int rowNum) throws SQLException {
		Likes likes = new Likes();
		likes.setId(rs.getInt("id"));
		likes.setPostId(rs.getInt("post_id"));
		likes.setUserName(rs.getString("user_name"));
		return likes;

	}
}
