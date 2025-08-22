package com.gaurav.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaurav.blog.repositories.*;
import com.gaurav.blog.payloads.UserDTO;
import com.gaurav.blog.entities.User;
import com.gaurav.blog.services.*;
import com.gaurav.blog.exceptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private ModelMapper modelMapper;

    // Implement the methods defined in UserService interface
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // Implementation logic here
        User user = this.convertToEntity(userDTO);
        // Save user to the database (not shown here)
        User savedUser = this.userRepo.save(user);

        return this.convertToDTO(savedUser);

    }

    @Override
    public UserDTO updateUser(Integer userId, UserDTO userDTO) {
        // Implementation logic here
        User existingUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setName(userDTO.getName());
        existingUser.setPassword(userDTO.getPassword());
        existingUser.setAbout(userDTO.getAbout());
        User updatedUser = this.userRepo.save(existingUser);
        return this.convertToDTO(updatedUser);

    }

    @Override
    public void deleteUser(Integer userId) {
          // Implementation logic here
        User existingUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(existingUser);
        
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        // Implementation logic here
         User existingUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return this.convertToDTO(existingUser);
      
    }

    @Override
    public List<UserDTO> getAllUsers() {
        // Implementation logic here
        List<User> users = this.userRepo.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> this.convertToDTO(user)).collect(Collectors.toList());
        // List<UserDTO> userDTOs = users.stream()
        //         .map(this::convertToDTO)
        //         .toList();
        return userDTOs;
        
    }

    private User convertToEntity(UserDTO userDTO) {

        //using model mapper
        User user = modelMapper.map(userDTO, User.class);


        // Convert UserDTO to User entity
        // User user = new User();
        // user.setName(userDTO.getName());
        // user.setEmail(userDTO.getEmail());
        // user.setPassword(userDTO.getPassword());
        // user.setAbout(userDTO.getAbout());
        return user;
    }

    private UserDTO convertToDTO(User user) {

        //using model mapper
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        // Convert User entity to UserDTO
        // UserDTO userDTO = new UserDTO();
        // userDTO.setId(user.getId());
        // userDTO.setName(user.getName());
        // userDTO.setEmail(user.getEmail());
        // userDTO.setPassword(user.getPassword());
        // userDTO.setAbout(user.getAbout());
        return userDTO;
    }
}
