package com.posts.service.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.posts.service.model.Post;

public class PostMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        Post post = new Post();
		post.setPostId(rs.getInt("id"));
        post.setUserName(rs.getString("user_name"));
        post.setCaption(rs.getString("caption"));
        post.setLocation(rs.getString("location"));
        post.setTags(rs.getString("tags"));
        return post;
    }

}
