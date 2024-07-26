package com.posts.service.service;

public interface LikesService {

	int likePost(String userName, int postId);

	int unLikePost(String userName, int postId);

}
