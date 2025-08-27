package com.gaurav.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gaurav.blog.config.AppConstants;
import com.gaurav.blog.payloads.CommentDTO;
import com.gaurav.blog.payloads.PostDTO;
import com.gaurav.blog.payloads.PostResponse;
import com.gaurav.blog.services.CommentService;
import com.gaurav.blog.services.FileService;
import com.gaurav.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;

    @Value("${project.image}")
    private String path;

    @Autowired
    private FileService fileService;
    

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> createPost(@Valid @PathVariable Integer userId, @PathVariable Integer categoryId,
            @RequestBody PostDTO postDTO) {
        PostDTO createdPost = this.postService.createPost(postDTO, userId, categoryId);
        return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userId) {
        List<PostDTO> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDTO> posts = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PostResponse posts = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {
        PostDTO post = this.postService.getPostById(postId);
        return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
    }

    @PutMapping("posts/{postId}")
    public ResponseEntity<PostDTO> updatePostById(@PathVariable Integer postId, @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = this.postService.updatePost(postId, postDTO);
        return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);

    }

    @DeleteMapping("posts/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPostsByTitle(@PathVariable String keyword) {
        List<PostDTO> result = this.postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
    }

    @GetMapping("/posts/customeSearch/{keyword}")
    public ResponseEntity<List<PostDTO>> customSearchPostsByTitle(@PathVariable String keyword) {
        List<PostDTO> result = this.postService.customSearchPosts(keyword);
        return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
    }

    @PostMapping("posts/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(@RequestParam MultipartFile image, @PathVariable Integer postId)
            throws IOException {
        String fileName = this.fileService.uploadImage(path, image);

        PostDTO postDTO = this.postService.getPostById(postId);
        postDTO.setImageName(fileName);
        PostDTO updatedPost = this.postService.updatePost(postId, postDTO);
        return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
    }

    @GetMapping("posts/image/{imageName}")
    public void getPostImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }



}
