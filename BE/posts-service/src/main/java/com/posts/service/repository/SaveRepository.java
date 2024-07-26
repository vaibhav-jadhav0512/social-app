package com.posts.service.repository;

public interface SaveRepository {

	int savePost(String userName, int postId);

	int unSavePost(String userName, int postId);

}
