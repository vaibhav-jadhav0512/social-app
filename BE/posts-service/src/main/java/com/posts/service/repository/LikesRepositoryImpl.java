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
import com.posts.service.repository.rowmapper.PostFileMapper;
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

	@Override
	public List<PostDto> likedPosts(String name) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", name);
		List<PostDto> list = template.query(PostQueries.LIKED_POSTS, paramMap, new PostFileMapper());
		for (PostDto post : list) {
			Map<String, Object> paramMaps = new HashMap<>();
			paramMaps.put("postId", post.getPostId());
			List<Files> files = template.query(PostQueries.GET_FILES_BY_POSTID, paramMaps, new FilesMapper());
			post.setFiles(files);
			List<Likes> likes = template.query(PostQueries.GET_LIKES_BY_POSTID, paramMaps, new LikesMapper());
			post.setLikes(likes);
		}
		return list;
	}

}
