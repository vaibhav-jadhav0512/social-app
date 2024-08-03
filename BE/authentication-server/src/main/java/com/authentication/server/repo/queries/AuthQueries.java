package com.authentication.server.repo.queries;

public class AuthQueries {

	public static final String FIND_BY_USER_NAME = "SELECT u.user_id, u.user_name, u.email, u.password, u.roles, ui.full_name "
			+ "FROM auth.users u INNER JOIN auth.user_info ui ON u.user_name = ui.user_name WHERE u.user_name=:userName";
	public static final String SAVE_REFRESH_TOKEN = "INSERT INTO auth.refresh_tokens "
			+ "(id, refresh_token, revoked, user_name) "
			+ "VALUES(nextval('auth.refresh_tokens_id_seq'::regclass),:refreshToken,:revoked,:userName)";
	public static final String GET_USER_BY_REFRESH_TOKEN = "SELECT u.user_id, u.user_name, u.email, u.password, u.roles, r.revoked, r.id, r.refresh_token, ui.full_name "
			+ "FROM" + "    auth.users u"
			+ "    INNER JOIN auth.refresh_tokens r ON u.user_name = r.user_name INNER JOIN auth.user_info ui ON u.user_name = ui.user_name "
			+ "WHERE r.refresh_token =:refreshToken";
	public static final String SAVE_USER = "INSERT INTO auth.users "
			+ "(user_id, user_name, email, \"password\")\n"
			+ "VALUES(nextval('auth.users_user_id_seq'::regclass), :userName, :email, :password);";
	public static final String SAVE_USER_INFO = "INSERT INTO auth.user_info " + "(id, user_name, full_name) "
			+ "VALUES(nextval('auth.user_info_id_seq'::regclass), :userName, :fullName);";
	public static String REVOKE_REFRESH_TOKEN = "UPDATE auth.refresh_tokens "
			+ "SET revoked=true WHERE refresh_token=:refreshToken";

}
