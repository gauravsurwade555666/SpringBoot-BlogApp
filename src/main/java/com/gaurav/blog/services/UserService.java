package com.gaurav.blog.services;
import com.gaurav.blog.payloads.UserDTO;
import java.util.List;

public interface UserService {
    //create
    UserDTO createUser(UserDTO userDTO);
    //update
    UserDTO updateUser(int userId, UserDTO userDTO);

    void deleteUser(int userId);

    UserDTO getUserById(int userId);

    List<UserDTO> getAllUsers();
}
