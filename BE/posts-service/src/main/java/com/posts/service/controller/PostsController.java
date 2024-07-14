package com.posts.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.posts.service.model.FileMetadata;
import com.posts.service.model.Post;
import com.posts.service.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostsController {

	@Autowired
	private PostService service;

	@PostMapping("/create")
	public ResponseEntity<Void> uploadFiles(@RequestBody Post post, @RequestParam("files") List<MultipartFile> files) {
		int postId = service.createPost(null);
		List<FileMetadata> fileMetadataList = new ArrayList<>();
		for (MultipartFile file : files) {
			try {
				String fileName = file.getOriginalFilename();
				String fileType = file.getContentType();
				byte[] fileData = file.getBytes();
				FileMetadata fileMetadata = new FileMetadata();
				fileMetadata.setPostId(postId);
				fileMetadata.setFileData(fileData);
				fileMetadata.setFileType(fileType);
				fileMetadata.setFileName(fileName);
				fileMetadataList.add(fileMetadata);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		service.insertFiles(fileMetadataList);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
