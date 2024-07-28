package com.posts.service.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.posts.service.model.Files;
import com.posts.service.model.Likes;
import com.posts.service.model.dto.PostDto;
import com.posts.service.repository.rowmapper.FilesMapper;
import com.posts.service.repository.rowmapper.LikesMapper;
import com.posts.service.repository.rowmapper.PostSaveMapper;
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

	@Override
	public List<PostDto> getSavedPostsByUserName(String userName) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userName);
		List<PostDto> list = template.query(PostQueries.GET_SAVED_POSTS_BY_USER_NAME, paramMap, new PostSaveMapper());
		for (PostDto post : list) {
			paramMap.put("postId", post.getPostId());
			List<Files> files = template.query(PostQueries.GET_FILES_BY_POSTID, paramMap, new FilesMapper());
			post.setFiles(files);
			List<Likes> likes = template.query(PostQueries.GET_LIKES_BY_POSTID, paramMap, new LikesMapper());
			post.setLikes(likes);
		}
		return list;
	}

}
