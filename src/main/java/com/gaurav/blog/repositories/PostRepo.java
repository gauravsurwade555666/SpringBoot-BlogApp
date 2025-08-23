package com.gaurav.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gaurav.blog.entities.Category;
import com.gaurav.blog.entities.Post;
import com.gaurav.blog.entities.User;


public interface PostRepo extends JpaRepository<Post, Integer> {

    //custom finder methods (if needed) can be defined here
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String keyword);

    //custom query using @Query annotation
    @Query("select p from Post p where p.title like :key")
    List<Post> customSearchTitle(@Param("key") String keyword);

}

