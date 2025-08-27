package com.gaurav.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
