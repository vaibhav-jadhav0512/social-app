package com.posts.service.service;

import java.util.List;

import com.posts.service.model.dto.PostDto;

public interface SaveService {

	void savePost(String userName, int postId);

	void unSavePost(String userName, int postId);

	List<PostDto> getSavedPostsByUserName(String userName);

}
