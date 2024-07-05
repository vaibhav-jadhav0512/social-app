package com.authentication.server.repo;

import com.authentication.server.model.UserInfo;

public interface AuthRepository {

	UserInfo findByUserName(String username);

}
