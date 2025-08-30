package com.gaurav.blog.payloads;
import lombok.AllArgsConstructor;
import lombok.Data;

// this class is used to send the token to the client
// after successful authentication

@Data
@AllArgsConstructor
public class JwtAuthResponse {
    private String token;
}
