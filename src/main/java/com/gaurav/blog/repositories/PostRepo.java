package com.gaurav.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.blog.entities.Post;

public interface PostRepo extends JpaRepository<Post, Integer> {

    //custom finder methods (if needed) can be defined here
    // List<Post> findByUser(Integer userId);
    // List<Post> findByCategory(Integer categoryId);
}
