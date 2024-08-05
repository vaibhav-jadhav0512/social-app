package com.user.info.service.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

@Service
public class CloudinaryService {
	@Autowired
	private Cloudinary cloudinary;

	public Map upload(MultipartFile file) {
		try {
			return this.cloudinary.uploader().upload(file.getBytes(), Map.of());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
