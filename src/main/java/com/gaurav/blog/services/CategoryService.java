package com.gaurav.blog.services;

import java.util.List;

import com.gaurav.blog.payloads.CategoryDTO;


public interface CategoryService {
    //create
    CategoryDTO createCategory(CategoryDTO CategoryDTO);

    //update
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

    //get category by id
    CategoryDTO getCategoryById(Integer categoryId);    

    //get all categories
    List<CategoryDTO> getAllCategories();

    //delete
    void deleteCategory(Integer categoryId);
}
