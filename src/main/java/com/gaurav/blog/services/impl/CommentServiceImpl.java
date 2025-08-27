package com.gaurav.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaurav.blog.entities.Comment;
import com.gaurav.blog.entities.Post;
import com.gaurav.blog.exceptions.ResourceNotFoundException;
import com.gaurav.blog.payloads.CommentDTO;
import com.gaurav.blog.repositories.CommentRepo;
import com.gaurav.blog.repositories.PostRepo;
import com.gaurav.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepo postRepo;

    @Override
    public CommentDTO createComment(Integer postId, CommentDTO commentDTO) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comments", "Id", postId));
        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comments", "Id", commentId));
        this.commentRepo.delete(comment);
    }

}
