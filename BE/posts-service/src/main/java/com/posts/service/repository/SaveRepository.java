package com.posts.service.repository;

import java.util.List;

import com.posts.service.model.dto.PostDto;

public interface SaveRepository {

	int savePost(String userName, int postId);

	int unSavePost(String userName, int postId);

	List<PostDto> getSavedPostsByUserName(String userName);

}
