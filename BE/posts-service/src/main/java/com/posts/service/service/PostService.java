package com.posts.service.service;

import java.util.List;

import com.posts.service.model.FileMetadata;
import com.posts.service.model.Post;

public interface PostService {

	int createPost(Post post);

	void insertFiles(List<FileMetadata> fileMetadataList);

}
