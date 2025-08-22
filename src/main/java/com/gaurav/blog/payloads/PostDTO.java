package com.gaurav.blog.payloads;

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
    private UserDTO user;
}
