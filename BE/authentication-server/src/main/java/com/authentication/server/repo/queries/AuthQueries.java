package com.authentication.server.repo.queries;

public class AuthQueries {

	public static final String FIND_BY_USER_NAME = "SELECT user_id, user_name, first_name, last_name, email, \"password\", mobile, roles "
			+ "FROM auth.users WHERE user_name=:userName";

}
