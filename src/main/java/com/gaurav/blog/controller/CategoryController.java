package com.gaurav.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.blog.payloads.CategoryDTO;
import com.gaurav.blog.services.CategoryService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/") 
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO CategoryDTO) {
        CategoryDTO createdCategory = this.categoryService.createCategory(CategoryDTO);
        return new ResponseEntity<CategoryDTO>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")    
    public ResponseEntity<CategoryDTO> getCategoryById(@Valid @PathVariable Integer categoryId) {
        CategoryDTO categoryDTO = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{categoryId}")    
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable Integer categoryId, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = this.categoryService.updateCategory( categoryDTO, categoryId);
        return new ResponseEntity<CategoryDTO>(updatedCategory,HttpStatus.OK);
    }
    
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories =this.categoryService.getAllCategories();
        return new ResponseEntity<List<CategoryDTO>>(categories,HttpStatus.OK);
    }
}
