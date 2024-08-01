package com.user.info.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.info.service.model.UserInfo;
import com.user.info.service.model.UserInfoDto;
import com.user.info.service.service.UserInfoService;

@RestController
@RequestMapping("/users")
public class UserInfoController {

	@Autowired
	private UserInfoService service;

	@GetMapping("/get/info")
	public ResponseEntity<UserInfo> getUserInfo(Authentication authentication) {
		return new ResponseEntity<>(service.getUserInfo(authentication.getName()), HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<Integer> updateUserInfo(@RequestBody UserInfo userInfo) {
		return new ResponseEntity<>(service.updateUserInfo(userInfo), HttpStatus.CREATED);
	}

	@GetMapping("/get/all")
	public ResponseEntity<List<UserInfoDto>> getAllUsers() {
		return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
	}
}
