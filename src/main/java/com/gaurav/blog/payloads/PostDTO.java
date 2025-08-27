package com.gaurav.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {

    private Integer postId;
    
    @NotNull
    private String title;
    @NotNull
    private String content;

    private String imageName;
    private String addedDate;

    private CategoryDTO category;

    //get call of Post will return only one user hence used UserDTO here.
    private UserDTO user;
    //get call of Post will return list of comments hence used Set here.
    private Set<CommentDTO> comments = new HashSet<>();
}
