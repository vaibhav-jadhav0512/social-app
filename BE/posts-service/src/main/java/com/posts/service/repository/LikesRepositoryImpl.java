package com.posts.service.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.posts.service.repository.sql.PostQueries;

@Repository
public class LikesRepositoryImpl implements LikesRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Override
	public int likePost(String userName, int postId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("postId", postId);
		paramMap.put("userName", userName);
		return template.update(PostQueries.LIKE_POST, paramMap);
	}

	@Override
	public int unLikePost(String userName, int postId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("postId", postId);
		paramMap.put("userName", userName);
		return template.update(PostQueries.UNLIKE_POST, paramMap);
	}

}
