package com.posts.service.repository;

import java.util.List;

import com.posts.service.model.FileMetadata;
import com.posts.service.model.Post;

public interface PostRepository {

	int createPost(Post post);

	void insertFiles(List<FileMetadata> fileMetadataList);

	Post getPostById(int postId);

}
