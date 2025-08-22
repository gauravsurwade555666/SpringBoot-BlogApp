package com.gaurav.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaurav.blog.entities.Category;
import com.gaurav.blog.exceptions.ResourceNotFoundException;
import com.gaurav.blog.payloads.CategoryDTO;
import com.gaurav.blog.repositories.CategoryRepo;
import com.gaurav.blog.services.CategoryService;



@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        // Implementation here
        Category Cat = this.modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = this.categoryRepository.save(Cat);
        return this.modelMapper.map(savedCategory, CategoryDTO.class);
      
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        // Implementation here
        Category existingCategory = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id",  categoryId));
       existingCategory.setCategoryTitle(categoryDTO.getCategoryTitle());
       existingCategory.setCategoryDescription(categoryDTO.getCategoryDescription());
       Category updatedCategory = this.categoryRepository.save(existingCategory);
       return this.modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryById(Integer categoryId) {
        // Implementation here
        Category existingCategory = this.categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "Id",  categoryId));   
        return this.modelMapper.map(existingCategory, CategoryDTO.class);
    }
    

    @Override
    public List<CategoryDTO> getAllCategories() {
        // Implementation here
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(category -> this.modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
        return categoryDTOs;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category existingCategory = this.categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "Id",  categoryId));   
        this.categoryRepository.delete(existingCategory);
    }

}
