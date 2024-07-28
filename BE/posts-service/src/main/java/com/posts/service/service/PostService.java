package com.posts.service.service;

import java.util.List;

import com.posts.service.model.FileMetadata;
import com.posts.service.model.Post;
import com.posts.service.model.dto.PostDto;

public interface PostService {

	int createPost(Post post);

	void insertFiles(List<FileMetadata> fileMetadataList);

	Post getPostById(int postId);

	List<PostDto> getRecentPosts();

	void updatePost(Post post);

	List<PostDto> getUserPosts(String userName);
}
