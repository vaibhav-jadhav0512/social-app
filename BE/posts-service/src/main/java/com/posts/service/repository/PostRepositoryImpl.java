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
import com.posts.service.model.Post;
import com.posts.service.repository.rowmapper.FileMapper;
import com.posts.service.repository.rowmapper.PostMapper;
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
	public Post getPostById(int postId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("postId", postId);
		Post post = template.queryForObject(PostQueries.GET_BY_POST_ID, paramMap, new PostMapper());
		List<FileMetadata> files = template.query(PostQueries.GET_FILES_BY_POST_ID, paramMap, new FileMapper());
		post.setFiles(files);
		return post;
	}

}
