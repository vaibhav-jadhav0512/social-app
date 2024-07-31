package com.user.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.info.service.model.UserInfo;
import com.user.info.service.service.UserInfoService;

@RestController
@RequestMapping("/users")
public class UserInfoController {

	@Autowired
	private UserInfoService service;

	@GetMapping("/get/info/{userName}")
	public ResponseEntity<UserInfo> getUserInfo(@PathVariable("userName") String userName) {
		return new ResponseEntity<>(service.getUserInfo(userName), HttpStatus.OK);
	}

}
