package com.posts.service.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.posts.service.model.FileMetadata;
import com.posts.service.model.Files;
import com.posts.service.model.Likes;
import com.posts.service.model.Post;
import com.posts.service.model.dto.PostDto;
import com.posts.service.repository.rowmapper.FilesMapper;
import com.posts.service.repository.rowmapper.LikesMapper;
import com.posts.service.repository.rowmapper.PostFileMapper;
import com.posts.service.repository.sql.PostQueries;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PostRepositoryImpl implements PostRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Override
	public int createPost(Post post) {
		log.info("Saving post to db: {}", post.toString());
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("userName", post.getUserName())
				.addValue("caption", post.getCaption()).addValue("location", post.getLocation())
				.addValue("tags", post.getTags());
		KeyHolder keyHolder = new GeneratedKeyHolder();

		template.update(PostQueries.SAVE_POST, paramMap, keyHolder);

		return keyHolder.getKey().intValue();

	}

	@Override
	public void insertFiles(List<FileMetadata> fileMetadataList) {
		for (FileMetadata fileMetadata : fileMetadataList) {
			MapSqlParameterSource paramMap = new MapSqlParameterSource()
					.addValue("postId", fileMetadata.getPostId()).addValue("url", fileMetadata.getUrl());
			template.update(PostQueries.INSERT_FILE, paramMap);
		}
	}

	@Override
	public PostDto getPostById(int postId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("postId", postId);
		PostDto post = template.queryForObject(PostQueries.GET_BY_POST_ID, paramMap, new PostFileMapper());
		List<Files> files = template.query(PostQueries.GET_FILES_BY_POSTID, paramMap, new FilesMapper());
		post.setFiles(files);
		List<Likes> likes = template.query(PostQueries.GET_LIKES_BY_POSTID, paramMap, new LikesMapper());
		post.setLikes(likes);
		return post;
	}

	@Override
	public List<PostDto> getRecentPosts() {

		List<PostDto> list = template.query(PostQueries.GET_RECENT_POSTS, new PostFileMapper());
		for (PostDto post : list) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("postId", post.getPostId());
			List<Files> files = template.query(PostQueries.GET_FILES_BY_POSTID, paramMap, new FilesMapper());
			post.setFiles(files);
			List<Likes> likes = template.query(PostQueries.GET_LIKES_BY_POSTID, paramMap, new LikesMapper());
			post.setLikes(likes);
		}
		return list;
	}

	@Override
	public void updatePost(Post post) {
		log.info("Updating post: {}", post.toString());
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("postId", post.getPostId());
		paramMap.put("caption", post.getCaption());
		paramMap.put("location", post.getLocation());
		paramMap.put("tags", post.getTags());
		template.update(PostQueries.UPDATE_POST, paramMap);
	}

	@Override
	public void deleteFiles(int postId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("postId", postId);
		template.update(PostQueries.DELETE_FILES, paramMap);
	}

	public List<PostDto> getUserPosts(String userName) {
		Map<String, Object> paramMaps = new HashMap<>();
		paramMaps.put("userName", userName);
		List<PostDto> list = template.query(PostQueries.GET_USER_POSTS, paramMaps, new PostFileMapper());
		for (PostDto post : list) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("postId", post.getPostId());
			List<Files> files = template.query(PostQueries.GET_FILES_BY_POSTID, paramMap, new FilesMapper());
			post.setFiles(files);
			List<Likes> likes = template.query(PostQueries.GET_LIKES_BY_POSTID, paramMap, new LikesMapper());
			post.setLikes(likes);
		}
		return list;
	}

	@Override
	public void deletePostById(int postId) {
		Map<String, Object> paramMaps = new HashMap<>();
		paramMaps.put("postId", postId);
		template.update(PostQueries.DELETE_POST_BY_ID, paramMaps);

	}

	@Override
	public List<PostDto> explore(int page) {
		Map<String, Object> paramMaps = new HashMap<>();
		paramMaps.put("page", (page - 1) * 5);
		List<PostDto> list = template.query(PostQueries.EXPLORE, paramMaps, new PostFileMapper());
		for (PostDto post : list) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("postId", post.getPostId());
			List<Files> files = template.query(PostQueries.GET_FILES_BY_POSTID, paramMap, new FilesMapper());
			post.setFiles(files);
			List<Likes> likes = template.query(PostQueries.GET_LIKES_BY_POSTID, paramMap, new LikesMapper());
			post.setLikes(likes);
		}
		return list;
	}

	@Override
	public List<PostDto> search(String keyword, int page) {
		Map<String, Object> paramMaps = new HashMap<>();
		paramMaps.put("page", (page - 1) * 5);
		paramMaps.put("keyword", "%" + keyword + "%");
		List<PostDto> list = template.query(PostQueries.SEARCH, paramMaps, new PostFileMapper());
		for (PostDto post : list) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("postId", post.getPostId());
			List<Files> files = template.query(PostQueries.GET_FILES_BY_POSTID, paramMap, new FilesMapper());
			post.setFiles(files);
			List<Likes> likes = template.query(PostQueries.GET_LIKES_BY_POSTID, paramMap, new LikesMapper());
			post.setLikes(likes);
		}
		return list;
	}

}
