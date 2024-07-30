package com.posts.service.repository.sql;

public class PostQueries {

	public static final String SAVE_POST = "INSERT INTO auth.posts "
			+ "(id, user_name, caption, \"location\", tags, created_at, updated_at) "
			+ "VALUES(nextval('auth.posts_id_seq'::regclass), :userName, :caption, :location, :tags, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) RETURNING id";
	public static final String INSERT_FILE = "INSERT INTO auth.files "
			+ "(id, url, post_id) " + "VALUES(nextval('auth.files_id_seq'::regclass), :url, :postId);";
	public static final String GET_BY_POST_ID = "SELECT id, user_name, caption, \"location\", tags, created_at "
			+ "FROM auth.posts WHERE id = :postId";
	public static final String GET_FILES_BY_POST_ID = "SELECT id, url, post_id "
			+ "FROM auth.files WHERE post_id=:postId";
	public static final String GET_RECENT_POSTS = "SELECT p.id, p.user_name, p.caption, p.location, p.tags, p.created_at, p.updated_at, "
			+ "f.id AS file_id, f.url AS file_url, f.post_id " + "FROM auth.posts p "
			+ "LEFT JOIN auth.files f ON p.id = f.post_id " + "ORDER BY p.updated_at DESC  LIMIT 20";
	public static final String GET_FILES_BY_POSTID = "SELECT id, url " + "FROM auth.files WHERE post_id=:postId";
	public static final String LIKE_POST = "INSERT INTO auth.likes " + "(id, user_name, post_id) "
			+ "VALUES(nextval('auth.likes_id_seq'::regclass), :userName, :postId);";
	public static final String UNLIKE_POST = "DELETE FROM auth.likes "
			+ "WHERE user_name=:userName AND post_id=:postId";
	public static final String GET_LIKES_BY_POSTID = "SELECT id, user_name, post_id "
			+ "FROM auth.likes WHERE post_id=:postId";
	public static final String SAVED_POST = "INSERT INTO auth.saved " + "(id, user_name, post_id) "
			+ "VALUES(nextval('auth.saved_id_seq'::regclass), :userName, :postId);";
	public static final String UNSAVE_POST = "DELETE FROM auth.saved WHERE user_name=:userName AND post_id=:postId";
	public static final String GET_SAVED_POSTS_BY_USER_NAME = "SELECT id, user_name, post_id "
			+ "FROM auth.saved WHERE user_name=:userName";
	public static final String UPDATE_POST = "UPDATE auth.posts SET "
			+ "caption=:caption, \"location\"=:location, tags=:tags, updated_at=CURRENT_TIMESTAMP WHERE id=:postId";
	public static final String DELETE_FILES = "DELETE FROM auth.files WHERE post_id=:postId";
	public static final String GET_USER_POSTS = "SELECT DISTINCT p.id, p.user_name, p.caption, p.location, p.tags, p.created_at, p.updated_at, "
			+ "f.id AS file_id, f.url AS file_url, f.post_id " + "FROM auth.posts p "
			+ "JOIN auth.likes l ON p.id=l.post_id LEFT JOIN auth.files f ON p.id = f.post_id AND p.user_name=:userName "
			+ "ORDER BY p.updated_at DESC";
	public static final String DELETE_POST_BY_ID = "DELETE FROM auth.posts WHERE id=:postId";
	public static final String EXPLORE = "SELECT DISTINCT p.id, p.user_name, p.caption, p.location, p.tags, p.created_at, p.updated_at, "
			+ "f.id AS file_id, f.url AS file_url, f.post_id " + "FROM auth.posts p "
			+ "JOIN auth.likes l ON p.id=l.post_id LEFT JOIN auth.files f ON p.id = f.post_id "
			+ "ORDER BY p.updated_at DESC LIMIT 6 OFFSET :page";
	public static final String SEARCH = "SELECT DISTINCT p.id, p.user_name, p.caption, p.location, p.tags, p.created_at, p.updated_at, "
			+ "f.id AS file_id, f.url AS file_url, f.post_id " + "FROM auth.posts p "
			+ "JOIN auth.likes l ON p.id=l.post_id LEFT JOIN auth.files f ON p.id = f.post_id WHERE UPPER(p.caption) LIKE UPPER(:keyword) "
			+ "ORDER BY p.updated_at DESC LIMIT 6 OFFSET :page";
}
