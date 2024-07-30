package com.posts.service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.posts.service.model.FileMetadata;
import com.posts.service.model.Post;
import com.posts.service.model.dto.PostDto;
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

	@PutMapping("/update")
	public ResponseEntity<Integer> updateFiles(@RequestParam("postId") int postId,
			@RequestParam("caption") String caption,
			@RequestParam("location") String location, @RequestParam("tags") String tags,
			@RequestParam(value = "files", required = false) MultipartFile[] files) {
		Post post = new Post();
		post.setPostId(postId);
		post.setCaption(caption);
		post.setLocation(location);
		post.setTags(tags);
		service.updatePost(post);
		System.out.println(post.toString());
		if (files != null) {
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
			System.out.println(fileMetadataList.toString());
			service.deleteFiles(postId);
			service.insertFiles(fileMetadataList);
		}
		return new ResponseEntity<>(postId, HttpStatus.CREATED);
	}

	@GetMapping("/get/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") int postId) {
		PostDto post = service.getPostById(postId);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/recent")
	public ResponseEntity<List<PostDto>> getRecentPosts() {
		List<PostDto> posts = service.getRecentPosts();
		if (posts != null) {
			return new ResponseEntity<>(posts, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/user/{userName}")
	public ResponseEntity<List<PostDto>> getUserPosts(@PathVariable("userName") String userName) {
		List<PostDto> posts = service.getUserPosts(userName);
		if (posts != null) {
			return new ResponseEntity<>(posts, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<Void> deletePostById(@PathVariable("postId") int postId) {
		service.deletePostById(postId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/explore")
	public ResponseEntity<List<PostDto>> explore(@RequestParam(value = "page", defaultValue = "0") int page) {
		return new ResponseEntity<>(service.explore(page), HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<PostDto>> search(@RequestParam(value = "keyword") String keyword) {
		return new ResponseEntity<>(service.search(keyword), HttpStatus.OK);
	}
}
