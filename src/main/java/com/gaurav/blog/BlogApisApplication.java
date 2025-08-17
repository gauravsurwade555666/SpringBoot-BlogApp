package com.gaurav.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;

@SpringBootApplication
public class BlogApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApisApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}

}
