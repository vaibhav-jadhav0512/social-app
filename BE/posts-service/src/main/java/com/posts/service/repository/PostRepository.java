package com.posts.service.repository;

import java.util.List;

import com.posts.service.model.FileMetadata;
import com.posts.service.model.Post;
import com.posts.service.model.dto.PostDto;

public interface PostRepository {

	int createPost(Post post);

	void insertFiles(List<FileMetadata> fileMetadataList);

	PostDto getPostById(int postId);

	List<PostDto> getRecentPosts();

	void updatePost(Post post);

	void deleteFiles(int postId);

	List<PostDto> getUserPosts(String userName);

	void deletePostById(int postId);

	List<PostDto> explore(int page);

	List<PostDto> search(String keyword);

}
