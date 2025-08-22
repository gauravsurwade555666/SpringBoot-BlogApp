package com.gaurav.blog.services;
import com.gaurav.blog.payloads.UserDTO;
import java.util.List;

public interface UserService {
    //create
    UserDTO createUser(UserDTO userDTO);
    //update
    UserDTO updateUser(Integer userId, UserDTO userDTO);

    void deleteUser(Integer userId);

    UserDTO getUserById(Integer userId);

    List<UserDTO> getAllUsers();
}
