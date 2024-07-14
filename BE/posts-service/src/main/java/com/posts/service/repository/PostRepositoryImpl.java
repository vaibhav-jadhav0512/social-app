package com.posts.service.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.posts.service.model.FileMetadata;
import com.posts.service.model.Post;
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
					.addValue("fileData", fileMetadata.getFileData()).addValue("fileName", fileMetadata.getFileName())
					.addValue("postId", fileMetadata.getPostId()).addValue("fileType", fileMetadata.getFileType());
			template.update(PostQueries.INSERT_FILE, paramMap);
		}
	}

}
