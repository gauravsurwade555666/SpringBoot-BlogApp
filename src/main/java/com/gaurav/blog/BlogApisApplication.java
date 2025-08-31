package com.gaurav.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gaurav.blog.entities.Role;
import com.gaurav.blog.repositories.RoleRepo;

import java.util.List;

import org.modelmapper.ModelMapper;

@SpringBootApplication
public class BlogApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder		passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApisApplication.class, args);
	}


	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(passwordEncoder.encode("gggggg"));
		Role role1 = new Role();
		role1.setId( 501);
		role1.setName("ROLE_ADMIN");
		Role role2 = new Role();
		role2.setId(502);
		role2.setName("ROLE_NORMAL");

		List<Role> roleList = List.of(role1, role2);
		roleList.forEach((role) -> {
			System.out.println(role.getName());
		});
		this.roleRepo.saveAll(roleList);
	}

}
