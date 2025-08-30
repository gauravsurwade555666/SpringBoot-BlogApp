package com.gaurav.blog.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // This method will be called whenever an exception is thrown due to an unauthenticated user trying to access a resource that requires authentication
    // We can use this method to send a custom response to the client, such as an error message or a redirect to a login page
    // We can also log the exception or perform any other necessary actions here
    // In this example, we will simply send a 401 Unauthorized response to the client       
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: " + authException.getMessage());
        
    }

}
