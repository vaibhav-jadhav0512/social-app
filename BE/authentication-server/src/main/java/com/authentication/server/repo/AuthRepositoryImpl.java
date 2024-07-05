package com.authentication.server.repo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.authentication.server.model.UserInfo;
import com.authentication.server.repo.queries.AuthQueries;
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
}
