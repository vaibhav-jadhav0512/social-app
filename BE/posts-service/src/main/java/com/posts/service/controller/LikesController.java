package com.posts.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posts.service.service.LikesService;

@RestController
@RequestMapping("/likes")
public class LikesController {

	@Autowired
	private LikesService service;

	@PostMapping("/like/{userName}/{postId}")
	public ResponseEntity<Integer> likePost(@PathVariable("userName") String userName,
			@PathVariable("postId") int postId) {
		return new ResponseEntity<>(service.likePost(userName, postId), HttpStatus.OK);
	}

	@PostMapping("/unlike/{userName}/{postId}")
	public ResponseEntity<Integer> unLikePost(@PathVariable("userName") String userName,
			@PathVariable("postId") int postId) {
		return new ResponseEntity<>(service.unLikePost(userName, postId), HttpStatus.OK);
	}

}
