package com.gaurav.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.blog.entities.Category;
import com.gaurav.blog.entities.Post;
import com.gaurav.blog.entities.User;


public interface PostRepo extends JpaRepository<Post, Integer> {

    //custom finder methods (if needed) can be defined here
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
}
