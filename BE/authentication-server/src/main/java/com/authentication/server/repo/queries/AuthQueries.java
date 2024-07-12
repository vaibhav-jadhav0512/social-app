package com.authentication.server.repo.queries;

public class AuthQueries {

	public static final String FIND_BY_USER_NAME = "SELECT user_id, user_name, first_name, last_name, email, \"password\", mobile, roles "
			+ "FROM auth.users WHERE user_name=:userName";
	public static final String SAVE_REFRESH_TOKEN = "INSERT INTO auth.refresh_tokens "
			+ "(id, refresh_token, revoked, user_name) "
			+ "VALUES(nextval('auth.refresh_tokens_id_seq'::regclass),:refreshToken,:revoked,:userName)";
	public static final String GET_USER_BY_REFRESH_TOKEN = "SELECT u.user_id, u.user_name, u.first_name, u.last_name, u.email, u.password, u.mobile, u.roles, r.revoked, r.id, r.refresh_token "
			+ "FROM" + "    auth.users u" + "    INNER JOIN auth.refresh_tokens r ON u.user_name = r.user_name "
			+ "WHERE r.refresh_token =:refreshToken";
	public static final String SAVE_USER = "INSERT INTO auth.users "
			+ "(user_id, user_name, name, email, \"password\", mobile)\n"
			+ "VALUES(nextval('auth.users_user_id_seq'::regclass), :userName,:name, :email, :password, :mobile);";
	public static String REVOKE_REFRESH_TOKEN = "UPDATE auth.refresh_tokens "
			+ "SET revoked=true WHERE refresh_token=:refreshToken";

}
