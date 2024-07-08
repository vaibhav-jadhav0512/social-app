package com.authentication.server.repo;

import com.authentication.server.model.UserInfo;
import com.authentication.server.model.dto.RefreshTokenDto;

public interface AuthRepository {

	UserInfo findByUserName(String username);

	void saveRefreshToken(RefreshTokenDto refreshTokenEntity);

	RefreshTokenDto findByRefreshToken(String refreshToken);

	void revokeRefreshToken(String refreshToken);

	void saveUser(UserInfo userDetails);

}
