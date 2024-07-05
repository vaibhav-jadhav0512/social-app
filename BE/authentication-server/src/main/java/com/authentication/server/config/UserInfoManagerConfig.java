package com.authentication.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authentication.server.model.UserInfo;
import com.authentication.server.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserInfoManagerConfig implements UserDetailsService {

	@Autowired
	private AuthService authService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo userInfo = authService.findByUserName(username);
		if (userInfo != null) {
			return new UserInfoConfig(userInfo);
		} else {
			throw new UsernameNotFoundException("UserName: " + username + " does not exist");
		}
	}

}
