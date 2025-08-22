package com.gaurav.blog.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.blog.entities.Category;


public interface CategoryRepo extends JpaRepository<Category,Integer>{
    
}
