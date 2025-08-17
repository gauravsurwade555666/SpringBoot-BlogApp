package com.gaurav.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gaurav.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    // com.gaurav.blog.entities.User findByEmail(String email);

}
