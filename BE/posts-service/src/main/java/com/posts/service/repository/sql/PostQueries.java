package com.posts.service.repository.sql;

public class PostQueries {

	public static final String SAVE_POST = "INSERT INTO auth.posts "
			+ "(id, user_name, caption, \"location\", tags, created_at, updated_at) "
			+ "VALUES(nextval('auth.posts_id_seq'::regclass), :userName, :caption, :location, :tags, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) RETURNING id";
	public static final String INSERT_FILE = "INSERT INTO auth.files "
			+ "(id, file_name, post_id, file_data, file_type) "
			+ "VALUES(nextval('auth.files_id_seq'::regclass), :fileName, :postId, :fileData, :fileType);";

}
