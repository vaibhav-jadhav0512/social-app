package com.user.info.service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.user.info.service.model.UserInfo;
import com.user.info.service.model.UserInfoDto;
import com.user.info.service.service.CloudinaryService;
import com.user.info.service.service.UserInfoService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST}, allowedHeaders = "*")
public class UserInfoController {

	@Autowired
	private UserInfoService service;

	@Autowired
	private CloudinaryService cloudinaryService;

	@GetMapping("/get/info")
	public ResponseEntity<UserInfo> getUserInfo(Authentication authentication) {
		return new ResponseEntity<>(service.getUserInfo(authentication.getName()), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<UserInfo> updateUserInfo(@RequestParam("userName") String userName,
			@RequestParam("fullName") String fullName, @RequestParam("bio") String bio,
			@RequestParam(value = "files", required = false) MultipartFile[] files) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserName(userName);
		userInfo.setFullName(fullName);
		userInfo.setBio(bio);
		service.updateUserInfo(userInfo);
		if (files != null) {
				try {
					Map upload = cloudinaryService.upload(files[0]);
					service.updateProfileImage(userName, upload.get("secure_url").toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return new ResponseEntity<>(service.getUserInfo(userName), HttpStatus.OK);
	}

	@GetMapping("/get/all")
	public ResponseEntity<List<UserInfoDto>> getAllUsers() {
		return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/get/{userName}")
	public ResponseEntity<UserInfo> getUserInfo(@PathVariable("userName") String userName) {
		return new ResponseEntity<>(service.getUserInfo(userName), HttpStatus.OK);
	}
}
