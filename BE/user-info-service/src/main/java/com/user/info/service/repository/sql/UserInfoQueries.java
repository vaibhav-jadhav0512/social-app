package com.user.info.service.repository.sql;

public class UserInfoQueries {

	public static final String GET_USER_INFO = "SELECT id, user_name, full_name, bio, mobile, profile_image "
			+ "FROM auth.user_info WHERE user_name=:userName";
	public static final String UPDATE_USER_INFO = "INSERT INTO auth.user_info (user_name, full_name, bio, mobile, profile_image) "
			+ "VALUES (:userName, :fullName, :bio, :mobile, :profileImage) " + "ON CONFLICT (user_name) DO UPDATE "
			+ "SET full_name = EXCLUDED.full_name, " + "    bio = EXCLUDED.bio, " + "    mobile = EXCLUDED.mobile, "
			+ "    profile_image = EXCLUDED.profile_image;";
	public static final String GET_ALL_USERS = "SELECT  u.user_name,  u.\"name\",  ui.profile_image "
			+ "FROM auth.users u LEFT JOIN auth.user_info ui ON u.user_name = ui.user_name";

}
