package com.posts.service.repository.sql;

public class PostQueries {

	public static final String SAVE_POST = "INSERT INTO auth.posts "
			+ "(id, user_name, caption, \"location\", tags, created_at, updated_at) "
			+ "VALUES(nextval('auth.posts_id_seq'::regclass), :userName, :caption, :location, :tags, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) RETURNING id";
	public static final String INSERT_FILE = "INSERT INTO auth.files "
			+ "(id, url, post_id) " + "VALUES(nextval('auth.files_id_seq'::regclass), :url, :postId);";
	public static final String GET_BY_POST_ID = "SELECT id, user_name, caption, \"location\", tags "
			+ "FROM auth.posts WHERE id = :postId";
	public static final String GET_FILES_BY_POST_ID = "SELECT id, url, post_id "
			+ "FROM auth.files WHERE post_id=:postId";
}
