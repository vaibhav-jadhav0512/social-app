package com.posts.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.posts.service.model.dto.PostDto;
import com.posts.service.repository.SaveRepository;

@Service
public class SaveServiceImpl implements SaveService {

	@Autowired
	private SaveRepository repo;

	@Override
	public void savePost(String userName, int postId) {
		repo.savePost(userName, postId);

	}

	@Override
	public void unSavePost(String userName, int postId) {
		repo.unSavePost(userName, postId);
	}

	@Override
	public List<PostDto> getSavedPostsByUserName(String userName) {
		return repo.getSavedPostsByUserName(userName);
	}

}
