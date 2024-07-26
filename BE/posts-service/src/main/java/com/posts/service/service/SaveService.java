package com.posts.service.service;

public interface SaveService {

	void savePost(String userName, int postId);

	void unSavePost(String userName, int postId);

}
