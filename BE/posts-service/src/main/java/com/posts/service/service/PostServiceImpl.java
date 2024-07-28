package com.posts.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.posts.service.model.FileMetadata;
import com.posts.service.model.Post;
import com.posts.service.model.dto.PostDto;
import com.posts.service.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository repo;

	@Override
	public int createPost(Post post) {
		return repo.createPost(post);
	}

	@Override
	public void insertFiles(List<FileMetadata> fileMetadataList) {
		repo.insertFiles(fileMetadataList);
	}

	@Override
	public Post getPostById(int postId) {
		return repo.getPostById(postId);
	}

	@Override
	public List<PostDto> getRecentPosts() {
		return repo.getRecentPosts();
	}

	@Override
	public void updatePost(Post post) {
		repo.updatePost(post);
	}

}
