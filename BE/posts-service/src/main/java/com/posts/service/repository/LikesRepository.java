package com.posts.service.repository;

public interface LikesRepository {

	int likePost(String userName, int postId);

	int unLikePost(String userName, int postId);

}
