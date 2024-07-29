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
	public PostDto getPostById(int postId) {
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

	@Override
	public void deleteFiles(int postId) {
		repo.deleteFiles(postId);
	}

	@Override
	public List<PostDto> getUserPosts(String userName) {
		return repo.getUserPosts(userName);
	}

	@Override
	public void deletePostById(int postId) {
		repo.deletePostById(postId);
	}

	@Override
	public List<PostDto> explore(int page) {
		return repo.explore(page);
	}

	@Override
	public List<PostDto> search(String keyword, int page) {
		return repo.search(keyword, page);
	}

}
