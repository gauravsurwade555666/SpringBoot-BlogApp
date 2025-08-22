package com.gaurav.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
     
    
    private Integer categoryID;
    @NotBlank
    @Size(min = 4, message = "Category Title must be at least 4 characters long")
    private String categoryTitle;

    @Size(min = 10, message = "Category Description must be at least 10 characters long")  
    private String categoryDescription;

}
