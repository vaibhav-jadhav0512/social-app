package com.user.info.service.repository.sql;

public class UserInfoQueries {

	public static final String GET_USER_INFO = "SELECT id, user_name, full_name, bio, mobile, profile_image "
			+ "FROM auth.user_info WHERE user_name=:userName";
	public static final String UPDATE_USER_INFO = "INSERT INTO auth.user_info (user_name, full_name, bio) "
			+ "VALUES (:userName, :fullName, :bio) " + "ON CONFLICT (user_name) DO UPDATE "
			+ "SET full_name = EXCLUDED.full_name, " + "    bio = EXCLUDED.bio";
	public static final String GET_ALL_USERS = "SELECT  u.user_name,  ui.profile_image, ui.full_name "
			+ "FROM auth.users u LEFT JOIN auth.user_info ui ON u.user_name = ui.user_name";
	public static final String UPDATE_PROFILE_IMAGE = "UPDATE auth.user_info SET profile_image=:url WHERE user_name=:userName";

}
