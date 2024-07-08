package com.authentication.server.repo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.authentication.server.model.UserInfo;
import com.authentication.server.model.dto.RefreshTokenDto;
import com.authentication.server.repo.queries.AuthQueries;
import com.authentication.server.repo.rowmapper.RefreshTokenMapper;
import com.authentication.server.repo.rowmapper.UserRowMapper;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class AuthRepositoryImpl implements AuthRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Override
	public UserInfo findByUserName(String username) {
		log.info("Finding by username: {}", username);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", username);
		try {
			UserInfo obj = template.queryForObject(AuthQueries.FIND_BY_USER_NAME, paramMap, new UserRowMapper());
			log.info("User:{}", obj.toString());
			return obj;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public void saveRefreshToken(RefreshTokenDto refreshToken) {
		log.info("Saving refresh token to db: {}", refreshToken.toString());
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("refreshToken", refreshToken.getRefreshToken());
		paramMap.put("userName", refreshToken.getUserInfo().getUserName());
		paramMap.put("revoked", refreshToken.isRevoked());
		template.update(AuthQueries.SAVE_REFRESH_TOKEN, paramMap);

	}

	@Override
	public RefreshTokenDto findByRefreshToken(String refreshToken) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("refreshToken", refreshToken);
		try {
			RefreshTokenDto obj = template.queryForObject(AuthQueries.GET_USER_BY_REFRESH_TOKEN, paramMap,
					new RefreshTokenMapper());
			log.info("Token info:{}", obj.toString());
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void revokeRefreshToken(String refreshToken) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("refreshToken", refreshToken);
		log.info("Logging out:{}", refreshToken);
		try {
			template.update(AuthQueries.REVOKE_REFRESH_TOKEN, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveUser(UserInfo userDetails) {
		log.info("Saving user to db: {}", userDetails.toString());
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", userDetails.getUserName());
		paramMap.put("firstName", userDetails.getFirstName());
		paramMap.put("lastName", userDetails.getLastName());
		paramMap.put("email", userDetails.getEmail());
		paramMap.put("mobile", userDetails.getMobile());
		paramMap.put("password", userDetails.getPassword());
		paramMap.put("roles", userDetails.getRoles());
		template.update(AuthQueries.SAVE_USER, paramMap);
	}
}
