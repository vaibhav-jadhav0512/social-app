package com.posts.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.posts.service.repository.LikesRepository;

@Service
public class LikesServiceImpl implements LikesService {

	@Autowired
	private LikesRepository repo;

	@Override
	public int likePost(String userName, int postId) {
		return repo.likePost(userName, postId);
	}

	@Override
	public int unLikePost(String userName, int postId) {
		return repo.unLikePost(userName, postId);
	}

}
