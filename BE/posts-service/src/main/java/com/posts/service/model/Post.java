package com.posts.service.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	private int postId;
	private String userName;
	private String caption;
	private String location;
	private String tags;
	private List<FileMetadata> files;
}
