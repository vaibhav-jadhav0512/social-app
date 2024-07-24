package com.posts.service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.posts.service.model.FileMetadata;
import com.posts.service.model.Post;
import com.posts.service.service.CloudinaryService;
import com.posts.service.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/posts")
@Slf4j
public class PostsController {

	@Autowired
	private PostService service;

	@Autowired
	private CloudinaryService cloudinaryService;

	@PostMapping("/create")
	public ResponseEntity<Void> uploadFiles(@RequestParam("userName") String userName,
			@RequestParam("caption") String caption, @RequestParam("location") String location,
			@RequestParam("tags") String tags, @RequestParam("files") MultipartFile[] files) {
		Post post = new Post();
		post.setUserName(userName);
		post.setCaption(caption);
		post.setLocation(location);
		post.setTags(tags);
		int postId = service.createPost(post);
		List<FileMetadata> fileMetadataList = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				Map upload = cloudinaryService.upload(file);
				FileMetadata fileMetadata = new FileMetadata();
				fileMetadata.setUrl(upload.get("secure_url").toString());
				fileMetadata.setPostId(postId);
				fileMetadataList.add(fileMetadata);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		service.insertFiles(fileMetadataList);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<Post> getPostById(@PathVariable("postId") int postId) {
        Post post = service.getPostById(postId);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}