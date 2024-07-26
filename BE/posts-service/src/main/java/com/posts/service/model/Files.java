package com.posts.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Files {
	private int id;
	private String imageUrl;
	private int postId;
}
