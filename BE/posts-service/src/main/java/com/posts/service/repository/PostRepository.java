package com.posts.service.repository;

import java.util.List;

import com.posts.service.model.FileMetadata;
import com.posts.service.model.Post;
import com.posts.service.model.dto.PostDto;

public interface PostRepository {

	int createPost(Post post);

	void insertFiles(List<FileMetadata> fileMetadataList);

	Post getPostById(int postId);

	List<PostDto> getRecentPosts();

	void updatePost(Post post);

	List<PostDto> getUserPosts(String userName);

}
