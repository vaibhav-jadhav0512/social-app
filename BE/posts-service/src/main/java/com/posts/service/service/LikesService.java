package com.posts.service.service;

import java.util.List;

import com.posts.service.model.dto.PostDto;

public interface LikesService {

	int likePost(String userName, int postId);

	int unLikePost(String userName, int postId);

	List<PostDto> likedPosts(String name);

}
