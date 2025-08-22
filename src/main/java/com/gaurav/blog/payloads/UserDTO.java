package com.gaurav.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Integer id;

    @NotNull
    @Size(min = 4, message = "Username must be at least 4 characters long")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @Size(min = 4, max = 10, message = "Password must be between 3 and 10 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Password must be alphanumeric")
    private String password;

    @NotNull
    private String about;

    
}
