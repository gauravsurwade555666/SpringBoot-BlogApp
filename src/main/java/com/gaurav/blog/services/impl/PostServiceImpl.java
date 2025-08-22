package com.gaurav.blog.services.impl;

import java.util.List;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaurav.blog.entities.Category;
import com.gaurav.blog.entities.Post;
import com.gaurav.blog.entities.User;
import com.gaurav.blog.exceptions.ResourceNotFoundException;
import com.gaurav.blog.payloads.PostDTO;
import com.gaurav.blog.repositories.CategoryRepo;
import com.gaurav.blog.repositories.UserRepo;
import com.gaurav.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    // create
    @Override
    public PostDTO createPost(PostDTO postDTO,Integer userId, Integer categoryId) {
        // Implementation here
        User existingUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Category existingCategory = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));  
            
        Post post = this.modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new java.util.Date());    
        post.setUser(existingUser);
        post.setCategory(existingCategory);
        return this.modelMapper.map(post, PostDTO.class);
    };

    // update
    @Override
    public PostDTO updatePost(Integer postId, PostDTO postDTO) {
        // Implementation here
        return null;
    };

    // delete
    @Override
    public void deletePost(Integer postId) {
        // Implementation here
        
    };

    // get
    @Override
    public PostDTO getPostById(Integer postId) {
        // Implementation here
        return null;
    };

    // get all
    @Override
    public List<PostDTO> getAllPosts() {
        // Implementation here
        return null;
    };

    // get by category
    @Override
    public List<PostDTO> getPostsByCategory(Integer categoryId) {
        // Implementation here
        return null;
    };

    // get by user
    @Override
    public List<PostDTO> getPostsByUser(Integer userId) {
        // Implementation here
        return null;
    };

    // search
    @Override
    public List<PostDTO> searchPosts(String keyword) {
        // Implementation here
        return null;
    };
}
