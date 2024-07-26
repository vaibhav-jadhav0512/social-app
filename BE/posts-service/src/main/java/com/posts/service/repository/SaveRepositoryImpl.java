package com.posts.service.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.posts.service.repository.sql.PostQueries;

@Repository
public class SaveRepositoryImpl implements SaveRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Override
	public int savePost(String userName, int postId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("postId", postId);
		paramMap.put("userName", userName);
		return template.update(PostQueries.SAVED_POST, paramMap);
	}

	@Override
	public int unSavePost(String userName, int postId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("postId", postId);
		paramMap.put("userName", userName);
		return template.update(PostQueries.UNSAVE_POST, paramMap);
	}

}
