package com.gaurav.blog.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.gaurav.blog.entities.Category;
import com.gaurav.blog.entities.Post;
import com.gaurav.blog.entities.User;
import com.gaurav.blog.exceptions.ResourceNotFoundException;
import com.gaurav.blog.payloads.PostDTO;
import com.gaurav.blog.payloads.PostResponse;
import com.gaurav.blog.repositories.CategoryRepo;
import com.gaurav.blog.repositories.PostRepo;
import com.gaurav.blog.repositories.UserRepo;
import com.gaurav.blog.services.PostService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class PostServiceImpl implements PostService {
        @Autowired
        private UserRepo userRepo;
        @Autowired
        private CategoryRepo categoryRepo;
        @Autowired
        private PostRepo postRepo;

        @Autowired
        private ModelMapper modelMapper;

        // create
        @Override
        public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {
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
                Post savedPost = this.postRepo.save(post);
                return this.modelMapper.map(savedPost, PostDTO.class);
        };

        // update
        @Override
        public PostDTO updatePost(Integer postId, PostDTO postDTO) {
                Post existingPost = this.postRepo.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
                existingPost.setTitle(postDTO.getTitle());
                existingPost.setContent(postDTO.getContent());
                existingPost.setImageName(postDTO.getImageName());
                Post updatedPost = this.postRepo.save(existingPost);
                return this.modelMapper.map(updatedPost, PostDTO.class);
        };

        // delete
        @Override
        public void deletePost(Integer postId) {
                Post existingPost = this.postRepo.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

                this.postRepo.delete(existingPost);
        };

        // get
        @Override
        public PostDTO getPostById(Integer postId) {

                Post existingPost = this.postRepo.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

                return this.modelMapper.map(existingPost, PostDTO.class);
        };

        // get all
        @Override
        public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
                // Implementation of pagination & SORTING
                Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();
                // Create Pageable instance
                Pageable p = PageRequest.of(pageNumber, pageSize, sort);
                // fetch data from database in form of page object
                Page<Post> pagePost = this.postRepo.findAll(p);
                // get content from page object
                List<Post> posts = pagePost.getContent();

                // List<Post> posts = this.postRepo.findAll();
                List<PostDTO> postDTOs = posts.stream()
                                .map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());

                PostResponse postResponse = new PostResponse();
                postResponse.setContent(postDTOs);
                postResponse.setPageNumber(pagePost.getNumber());
                postResponse.setPageSize(pagePost.getSize());
                postResponse.setTotalElements(pagePost.getTotalElements());
                postResponse.setTotalPages(pagePost.getTotalPages());
                postResponse.setLastPage(pagePost.isLast());

                return postResponse;
        };

        // get by category
        @Override
        public List<PostDTO> getPostsByCategory(Integer categoryId) {
                Category existingCategory = this.categoryRepo.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
                List<Post> posts = this.postRepo.findByCategory(existingCategory);
                List<PostDTO> postDTOs = posts.stream()
                                .map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());
                return postDTOs;
        };

        // get by user
        @Override
        public List<PostDTO> getPostsByUser(Integer userId) {
                User existingUser = this.userRepo.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
                List<Post> posts = this.postRepo.findByUser(existingUser);

                List<PostDTO> postDTOs = posts.stream()
                                .map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());
                return postDTOs;
        };

        // search
        @Override
        public List<PostDTO> searchPosts(String keyword) {
                List<Post> posts = this.postRepo.findByTitleContaining(keyword);
                List<PostDTO> postDTOs = posts.stream()
                                .map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());
                return postDTOs;

        };

        @Override
        public List<PostDTO> customSearchPosts(String keyword) {
                List<Post> posts = this.postRepo.customSearchTitle("%" + keyword + "%");
                List<PostDTO> postDTOs = posts.stream()
                                .map(post -> this.modelMapper.map(post, PostDTO.class))
                                .collect(Collectors.toList());
                return postDTOs;
        }
}
