package com.posts.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posts.service.model.dto.PostDto;
import com.posts.service.service.LikesService;

@RestController
@RequestMapping("/likes")
public class LikesController {

	@Autowired
	private LikesService service;

	@PostMapping("/like/{userName}/{postId}")
	public ResponseEntity<Integer> likePost(@PathVariable("userName") String userName,
			@PathVariable("postId") int postId) {
		service.likePost(userName, postId);
		return new ResponseEntity<>(postId, HttpStatus.OK);
	}

	@DeleteMapping("/unlike/{userName}/{postId}")
	public ResponseEntity<Integer> unLikePost(@PathVariable("userName") String userName,
			@PathVariable("postId") int postId) {
		service.unLikePost(userName, postId);
		return new ResponseEntity<>(postId, HttpStatus.OK);
	}

	@GetMapping("/liked/posts")
	public ResponseEntity<List<PostDto>> likedPosts(Authentication authentication) {
		return new ResponseEntity<>(service.likedPosts(authentication.getName()), HttpStatus.OK);
	}

}
