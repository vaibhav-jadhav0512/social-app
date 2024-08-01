package com.posts.service.model.dto;

import java.util.Date;
import java.util.List;

import com.posts.service.model.Files;
import com.posts.service.model.Likes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private int postId;
	private String userName;
	private String caption;
	private String location;
	private String tags;
	private Date createdAt;
	private List<Likes> likes;
	private List<Files> files;
	private String profileImage;

}
