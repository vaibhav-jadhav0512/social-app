package com.posts.service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

	@Bean
	public Cloudinary getCloudinary() {
		Map map = new HashMap();
		map.put("cloud_name", "dncbs5vqt");
		map.put("api_key", "129611536458421");
		map.put("api_secret", "YvYbr8TukbELvdrqfXhwkHLCcQ8");
		map.put("secure", true);

		return new Cloudinary(map);
	}
}
