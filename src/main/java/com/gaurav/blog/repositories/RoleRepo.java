package com.gaurav.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaurav.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
