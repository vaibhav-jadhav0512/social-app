package com.posts.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posts.service.model.dto.PostDto;
import com.posts.service.service.SaveService;

@RestController
@RequestMapping("/saver")
public class SaverController {

	@Autowired
	private SaveService service;

	@PostMapping("/save/{userName}/{postId}")
	public ResponseEntity<Integer> savePost(@PathVariable("userName") String userName,
			@PathVariable("postId") int postId) {
		service.savePost(userName, postId);
		return new ResponseEntity<>(postId, HttpStatus.OK);
	}

	@DeleteMapping("/unsave/{userName}/{postId}")
	public ResponseEntity<Integer> unSavePost(@PathVariable("userName") String userName,
			@PathVariable("postId") int postId) {
		service.unSavePost(userName, postId);
		return new ResponseEntity<>(postId, HttpStatus.OK);
	}

	@GetMapping("/get/{userName}")
	public ResponseEntity<List<PostDto>> getSavedPostsByUserName(@PathVariable("userName") String userName) {
		return new ResponseEntity<>(service.getSavedPostsByUserName(userName), HttpStatus.OK);
	}
}
