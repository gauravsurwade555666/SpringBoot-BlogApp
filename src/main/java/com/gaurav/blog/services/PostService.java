package com.gaurav.blog.services;

import java.util.List;


import com.gaurav.blog.payloads.PostDTO;
import com.gaurav.blog.payloads.PostResponse;

public interface PostService {

    //create
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
    //update    
    PostDTO updatePost(Integer postId, PostDTO postDTO);
    //delete
    void deletePost(Integer postId);
    //get
    PostDTO getPostById(Integer postId);
    //get all
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    //get by category
    List<PostDTO> getPostsByCategory(Integer categoryId);
    //get by user
    List<PostDTO> getPostsByUser(Integer userId);
    //search
    List<PostDTO> searchPosts(String keyword);
}
