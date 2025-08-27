package com.gaurav.blog.services;

import com.gaurav.blog.payloads.CommentDTO;

public interface CommentService {

    public CommentDTO createComment(Integer postId, CommentDTO commentDTO);
    public void deleteComment(Integer commentId);
}
